val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
  java
  `java-test-fixtures`
  `jvm-test-suite`
}

// Use jvm-test-suite to configure additional test suite integrationTest
// https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html#jvm_test_suite_plugin
testing {
  suites { 
    register<JvmTestSuite>("integrationTest") { 
      useJUnitJupiter(versionCatalog.findVersion("junit").get().toString())

      testType = TestSuiteType.INTEGRATION_TEST

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
          }
        }
      }
    }
  }
}

tasks.named("check") {
  dependsOn(testing.suites.named("integrationTest"))
}
