val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
  java
  `jvm-test-suite`
  id("com.gradle.cucumber.companion")
}

// Configure default test target to support Cucumber Tests
// https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html#jvm_test_suite_plugin
testing {
  suites {
    @Suppress("UNUSED_VARIABLE")
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter(versionCatalog.findVersion("junit").get().toString())

      testType = TestSuiteType.FUNCTIONAL_TEST

      dependencies {
        implementation(project())
        implementation(platform(versionCatalog.findLibrary("junit-bom").get()))
        implementation(versionCatalog.findLibrary("junit-jupiterApi").get())
        runtimeOnly(versionCatalog.findLibrary("junit-jupiterEngine").get())
        runtimeOnly(versionCatalog.findLibrary("junit-platformLauncher").get())
        implementation(versionCatalog.findLibrary("junit-platformSuiteApi").get())
        implementation(versionCatalog.findLibrary("cucumber-java").get())
        implementation(versionCatalog.findLibrary("cucumber-junit").get())
        implementation(versionCatalog.findLibrary("cucumber-junitPlatformEngine").get())
        runtimeOnly(versionCatalog.findLibrary("junit-platformSuiteEngine").get())
      }

      targets {
        all {
          testTask.configure {
            useJUnitPlatform()
            systemProperty("cucumber.junit-platform.naming-strategy", "long")
            testLogging {
                events("passed", "skipped", "failed")
            }
          }
        }
      }
    }
  }
}
