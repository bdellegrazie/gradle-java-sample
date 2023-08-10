import org.cyclonedx.gradle.CycloneDxTask

plugins {
    id("org.cyclonedx.bom")
}

tasks.withType<CycloneDxTask>().configureEach {
    setIncludeConfigs(listOf("runtimeClasspath"))
    setSkipConfigs(listOf("compileClasspath", "testCompileClasspath"))
    setProjectType("library")

    setIncludeLicenseText(false)
    setIncludeBomSerialNumber(false)
}
