plugins {
    id("org.sonarqube")
}

sonar {
    properties {
        property("sonar.projectKey", rootProject.name + ":" + project.name)
        property("sonar.projectName", rootProject.name + ":" + project.name)
        property("sonar.sourceEncoding", "UTF-8")
    }
}
