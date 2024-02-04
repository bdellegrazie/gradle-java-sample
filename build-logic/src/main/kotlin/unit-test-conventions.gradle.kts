val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
  // Apply the java Plugin to add support for Java.
  java
  `jvm-test-suite`
}

testing {
  suites {
    // Configure the built-in test suite
    @Suppress("UNUSED_VARIABLE")
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter(versionCatalog.findVersion("junit").get().toString())

      dependencies {
        // Test dependencies
        implementation(platform(versionCatalog.findLibrary("junit-bom").get()))
        implementation(versionCatalog.findLibrary("junit-jupiterApi").get())
        runtimeOnly(versionCatalog.findLibrary("junit-jupiterEngine").get())
        runtimeOnly(versionCatalog.findLibrary("junit-platformLauncher").get())
      }

      targets {
        all {
          testTask.configure {
            useJUnitPlatform()
          }
        }
      }
    }
  }
}
