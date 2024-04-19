val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
  java
  jacoco
  `java-test-fixtures`
  `jvm-test-suite`
}

// Configure default test target to support Cucumber Tests
// https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html#jvm_test_suite_plugin
testing {
  suites {
    val functionalTest by registering(JvmTestSuite::class) {
      useJUnitJupiter(versionCatalog.findVersion("junit").get().toString())

      testType = TestSuiteType.FUNCTIONAL_TEST

      dependencies {
        implementation(project())
        implementation(testFixtures(project()))
        implementation(platform(versionCatalog.findLibrary("junit-bom").get()))
        implementation(versionCatalog.findLibrary("junit-jupiterApi").get())
        implementation(versionCatalog.findLibrary("junit-platformSuiteApi").get())
        implementation(versionCatalog.findLibrary("cucumber-java").get())
        implementation(versionCatalog.findLibrary("cucumber-junit").get())
        implementation(versionCatalog.findLibrary("cucumber-junitPlatformEngine").get())
        runtimeOnly(versionCatalog.findLibrary("junit-jupiterEngine").get())
        runtimeOnly(versionCatalog.findLibrary("junit-platformLauncher").get())
        runtimeOnly(versionCatalog.findLibrary("junit-platformSuiteEngine").get())
      }

      //generateCucumberSuiteCompanion(project)

      targets {
        all {
          testTask.configure {
            useJUnitPlatform()
            systemProperty("cucumber.junit-platform.naming-strategy", "long")
            systemProperty("cucumber.publish.quiet", "true")
            testLogging {
              events("passed", "skipped", "failed")
            }
            shouldRunAfter("test")
            finalizedBy(jacocoFunctionalTestReport) // report is always generated after tests run
          }
        }
      }
    }
  }
}

tasks.named("check") { 
  dependsOn(testing.suites.named("functionalTest"))
}

val jacocoFunctionalTestReport = tasks.register<JacocoReport>("jacocoFunctionalTestReport") {
  val functionalTest = tasks.named("functionalTest")
  dependsOn(functionalTest) // tests are required to run before generating the report
  executionData(functionalTest.get())
  sourceSets(sourceSets.main.get())

  reports {
    csv.required = System.getenv("CI") != null
    html.required = System.getenv("CI") == null
    xml.required = System.getenv("CI") != null
  }
}
