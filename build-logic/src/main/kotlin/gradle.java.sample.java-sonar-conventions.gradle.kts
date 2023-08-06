plugins {
    id("org.sonarqube")
}

sonar {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.links.scm", "https://github.com/bdellegrazie/gradle-java-sample")
        property("sonar.links.issue", "https://github.com/bdellegrazie/gradle-java-sample/issues")
        property("sonar.dependencyCheck.jsonReportPath", "$buildDir/reports/dependency-check-report.json")
        property("sonar.dependencyCheck.htmlReportPath", "$buildDir/reports/dependency-check-report.html")
    }
}
