/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("library-conventions")
    alias(libs.plugins.cucumberCompanion)
}

dependencies {
    testImplementation(platform(libs.cucumber.bom))
    testImplementation(libs.junit.platformSuiteApi)
    testImplementation(libs.cucumber.java)
    testImplementation(libs.cucumber.junit)
    testImplementation(libs.cucumber.junitPlatformEngine)
    testRuntimeOnly(libs.junit.platformSuiteEngine)
}

tasks.withType<Test> {
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}
