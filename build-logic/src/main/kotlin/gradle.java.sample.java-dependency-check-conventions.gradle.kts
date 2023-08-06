import java.io.File
import java.io.FileInputStream
import java.util.*
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.reporting.ReportGenerator

plugins {
    id("org.owasp.dependencycheck")
}

dependencyCheck {
    formats = listOf(ReportGenerator.Format.HTML.toString(), ReportGenerator.Format.JSON.toString(), ReportGenerator.Format.JENKINS.toString())
    suppressionFile = file("$rootDir/config/dependency-check/suppressions.xml").toString()
    // Compile / Runtime classpath only.
    scanConfigurations = listOf("annotationProcessor", "compileClasspath", "runtimeClasspath")
    //skipConfigurations = listOf("checkstyle", "pmd", "pmdAux", "spotbugs")
    if (hasProperty("dependencyCheckProps")) {
        val prop = Properties().apply() {
            load(FileInputStream(File(property("dependencyCheckProps") as String)))
        }
        autoUpdate = false
        // These properties match those in the default properties file in DependencyCheck CLI
        data.connectionString = prop.getProperty("data.connection_string", "")
        data.username = prop.getProperty("data.user", "")
        data.password = prop.getProperty("data.password", "")
        data.driver = prop.getProperty("data.driver_name", "")
        data.driverPath = prop.getProperty("data.driverPath", "")
    }
}
