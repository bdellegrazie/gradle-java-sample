plugins {
  java
  `jvm-test-suite`
}

// Use jvm-test-suite to configure additional test suite integrationTest
// https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html#jvm_test_suite_plugin
testing {
  suites { 
    val test by getting(JvmTestSuite::class) { 
      useJUnitJupiter() 
    }

    register<JvmTestSuite>("integrationTest") { 
      useJUnitJupiter()

      testType.set(TestSuiteType.INTEGRATION_TEST) 

      sources { 
        java { 
            setSrcDirs(listOf("src/it/java")) 
        }
      }

      dependencies {
        implementation(project()) 
      }

      targets { 
        all {
          testTask.configure {
            shouldRunAfter(test)
          }
        }
      }
    }
  }
}

tasks.named("check") {
  dependsOn(testing.suites.named("integrationTest"))
}
