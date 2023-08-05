import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.reporting.ReportGenerator

val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("org.owasp.dependencycheck")
}

dependencyCheck {
    formats = listOf(ReportGenerator.Format.HTML.toString(), ReportGenerator.Format.JSON.toString(), ReportGenerator.Format.JENKINS.toString())
    suppressionFile = file("$rootDir/config/dependency-check/suppressions.xml").toString()
    data.connectionString = System.getenv("DC_DATA_CONNECTION_STRING")
    data.username = System.getenv("DC_DATA_USER")
    data.password = System.getenv("DC_DATA_PASSWORD")
    data.driver = System.getenv("DC_DATA_DRIVER")
    data.driverPath = System.getenv("DC_DATA_DRIVERPATH")
}
