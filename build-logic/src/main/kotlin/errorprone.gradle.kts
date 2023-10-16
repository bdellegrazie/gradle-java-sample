// https://errorprone.info/
// https://github.com/tbroyer/gradle-errorprone-plugin

import net.ltgt.gradle.errorprone.errorprone

val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
  id("net.ltgt.errorprone")
}

repositories {
  mavenCentral()
}

dependencies {
  errorprone(versionCatalog.findLibrary("errorprone").get())
}

tasks.withType<JavaCompile>().configureEach {
  options.errorprone.allErrorsAsWarnings.set(true)
  options.errorprone.disableWarningsInGeneratedCode.set(true)
}
