/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("application-conventions")
}

dependencies {
    implementation(libs.apache.commonsText)
    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass.set("gradle.java.sample.app.App")
}
