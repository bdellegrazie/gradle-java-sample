import org.cyclonedx.gradle.CycloneDxTask

plugins {
    id("org.cyclonedx.bom")
}

tasks.withType<CycloneDxTask>().configureEach {
    includeConfigs = listOf("runtimeClasspath")
    skipConfigs = listOf("compileClasspath", "testCompileClasspath")
    projectType = "library"

    includeLicenseText = false
    includeBomSerialNumber = false
}
