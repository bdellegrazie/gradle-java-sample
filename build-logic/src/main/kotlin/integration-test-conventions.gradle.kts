val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
  java
  `jvm-test-suite`
}

// Use jvm-test-suite to configure additional test suite integrationTest
// https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html#jvm_test_suite_plugin
testing {
  suites { 
    register<JvmTestSuite>("integrationTest") { 
      useJUnitJupiter(versionCatalog.findVersion("junit").get().toString())

      testType = TestSuiteType.INTEGRATION_TEST

      sources { 
        java { 
            setSrcDirs(listOf("src/it/java")) 
        }
      }

      dependencies {
        implementation(project())
        implementation(platform(versionCatalog.findLibrary("junit-bom").get()))
        implementation(versionCatalog.findLibrary("junit-jupiterApi").get())
        runtimeOnly(versionCatalog.findLibrary("junit-jupiterEngine").get())
        runtimeOnly(versionCatalog.findLibrary("junit-platformLauncher").get())
      }

      targets { 
        all {
          testTask.configure {
            useJUnitPlatform()
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
