pipeline {
  agent any
  options {
    ansiColor('xterm')
  }
  stages {
    stage("Checkout") {
      steps {
        checkout scm
      }
    }
    stage("Metadata") {
      environment {
        GIT_COMMIT_SHORT = "${env.GIT_COMMIT.take(7)}"
      }
      steps {
        //buildName "#${BUILD_NUMBER} (${GIT_COMMIT_SHORT})"
        discoverGitReferenceBuild()
        mineRepository()
        gitDiffStat()
      }
    }
    stage("Build") {
      steps {
        sh script: '''
          ./gradlew check
        '''
      }
      post {
        always {
          junit allowEmptyResults: true, checksName: 'Unit', skipPublishingChecks: true, testResults: '**/build/test-results/test/*.xml'
          junit allowEmptyResults: true, checksName: 'Integration', skipPublishingChecks: true, testResults: '**/build/test-results/integrationTest/*.xml'
          recordCoverage sourceCodeRetention: 'NEVER', tools: [[pattern: '**/build/reports/jacoco/**/*.xml']]
          recordIssues aggregatingResults: true, skipBlames: true, sourceCodeEncoding: 'UTF-8',
            tools: [ 
              checkStyle(pattern: '**/build/reports/checkstyle/**/*.xml', reportEncoding: 'UTF-8'),
              pmdParser(pattern: '**/build/reports/pmd/*.xml', reportEncoding: 'UTF-8'),
              spotBugs(pattern: '**/build/reports/spotbugs/*.xml', reportEncoding: 'UTF-8', useRankAsPriority: true),
              taskScanner(highTags:'FIXME', normalTags:'TODO', includePattern: '**/*.java', excludePattern: '**/build/**/*'),
            ]
        }
      }
    }
    stage("DependencyCheck") {
      environment {
        DB_DC=credentials('dependency_check_db_user')
      }
      steps {
        configFileProvider([configFile(fileId: 'dependency-check-props', replaceTokens: true, targetLocation: 'db.properties', variable: 'DB_DC_FILE')]) {
          sh script: '''
            ./gradlew -PdependencyCheckProps=${DB_DC_FILE} dependencyCheckAnalyze
          '''
        }
      }
      post {
        always {
          dependencyCheckPublisher failedNewCritical: 1, 
            failedTotalCritical: 1, 
            stopBuild: true, 
            newThresholdAnalysisExploitable: true, 
            totalThresholdAnalysisExploitable: true, 
            unstableNewCritical: 1, 
            unstableTotalCritical: 1
        }
      }
    }
    stage("Sonar") {
      steps {
        withSonarQubeEnv('default') {
            sh script: '''
            ./gradlew sonar ${CHANGE_ID:+-Psonar.newCode.referenceBranch=${CHANGE_TARGET}}
            '''
        }
      }
    }
    stage("DependencyTrack") {
      environment {
        GIT_COMMIT_SHORT = "${env.GIT_COMMIT.take(7)}"
      }
      steps {
        sh script: '''
          ./gradlew cycloneDxBom
        '''
      }
      post {
        always {
          dependencyTrackPublisher artifact: 'app/build/reports/bom.xml', 
            autoCreateProjects: true,
            projectId: '70c7c4ab-e919-4a98-8696-8d57dab876aa',
            projectName: 'gradle-java-sample', 
            projectVersion: '${GIT_COMMIT_SHORT}',
            synchronous: true
        }
      }
    }
  }
}
