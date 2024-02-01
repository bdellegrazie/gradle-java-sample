plugins {
    id("org.sonarqube")
}

sonar {
    properties {
        property("sonar.java.checkstyle.reportPaths", listOf(layout.buildDirectory.file("reports/checkstyle/main.xml").get(), layout.buildDirectory.file("reports/checkstyle/test.xml").get()))
        property("sonar.java.pmd.reportPaths", listOf(layout.buildDirectory.file("reports/pmd/main.xml").get(), layout.buildDirectory.file("reports/pmd/test.xml").get()))
        property("sonar.java.spotbugs.reportPaths", listOf(layout.buildDirectory.file("reports/spotbugs/main.xml").get(), layout.buildDirectory.file("reports/spotbugs/test.xml").get()))
    }
}
