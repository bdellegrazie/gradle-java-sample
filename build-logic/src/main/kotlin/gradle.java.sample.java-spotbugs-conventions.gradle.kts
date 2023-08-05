import com.github.spotbugs.snom.SpotBugsTask

val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.github.spotbugs")
}

spotbugs {
    ignoreFailures.set(System.getenv("CI") != null)
    showProgress.set(System.getenv("CI") == null)
    effort.set(com.github.spotbugs.snom.Effort.DEFAULT)
    reportLevel.set(com.github.spotbugs.snom.Confidence.DEFAULT)
}

dependencies {
    spotbugs(versionCatalog.findLibrary("spotbugs").get())
    spotbugsPlugins(versionCatalog.findLibrary("findsecbugs").get())
}

tasks.withType<SpotBugsTask>().configureEach {
    reports.create("html") {
        required.set(System.getenv("CI") == null)
    }
    reports.create("sarif") {
        required.set(System.getenv("CI") != null)
    }
    reports.create("text") {
        required.set(false)
    }
    reports.create("xml") {
        required.set(System.getenv("CI") != null)
    }
}
