plugins {
    id("org.sonarqube")
}

sonar {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "")  // nothing in the root
        property("sonar.dependencyCheck.jsonReportPath", "${buildDir}/reports/dependency-check-report.json")
        property("sonar.dependencyCheck.htmlReportPath", "${buildDir}/reports/dependency-check-report.html")
    }
}
