plugins {
    id("library-conventions")
    id("functional-test-conventions")
}

dependencies {
    testFixturesImplementation(platform(libs.junit.bom))
    testFixturesImplementation(libs.junit.jupiterApi)
    testFixturesImplementation(libs.junit.platformSuiteApi)
    testFixturesImplementation(libs.cucumber.java)
    testFixturesImplementation(libs.cucumber.junit)
    testFixturesImplementation(libs.cucumber.junitPlatformEngine)
}
