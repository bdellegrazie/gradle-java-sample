pipeline {
  agent any
  options {
    ansiColor('xterm')
  }
  stages {
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
              spotBugs(pattern: '**/build/spotbugs/*.xml', reportEncoding: 'UTF-8', useRankAsPriority: true)
            ]
        }
      }
    }
    stage("Sonar") {
      steps {
        withSonarQubeEnv('default') {
            sh script: '''
            ./gradlew sonarqube
            '''
        }
      }
    }
    stage("QualityGate") {
      steps {
        timeout(time: 1, unit: 'HOURS') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
    stage("Security") {
      environment {
        DB_DC=credentials('dependency_check_db_user')
      }
      steps {
        configFileProvider([configFile(fileId: 'dependency-check-props', replaceTokens: true, targetLocation: 'db.properties', variable: 'DB_DC_FILE')]) {
          dependencyCheck additionalArguments: "--propertyfile '${env.DB_DC_FILE}' --noupdate --scan .", odcInstallation: 'v8'
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
  }
}
