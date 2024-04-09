import com.github.spotbugs.snom.SpotBugsTask

val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.github.spotbugs")
}

spotbugs {
    ignoreFailures = System.getenv("CI") != null
    showProgress = System.getenv("CI") == null
    effort = com.github.spotbugs.snom.Effort.DEFAULT
    reportLevel = com.github.spotbugs.snom.Confidence.DEFAULT
}

dependencies {
    spotbugs(versionCatalog.findLibrary("spotbugs").get())
    spotbugsPlugins(versionCatalog.findLibrary("findsecbugs").get())
}

tasks.withType<SpotBugsTask>().configureEach {
    enabled = (findProperty("spotbugsEnabled") as String).toBoolean()

    reports.create("html") {
        required = System.getenv("CI") == null
    }
    reports.create("sarif") {
        required = System.getenv("CI") != null
    }
    reports.create("text") {
        required = false
    }
    reports.create("xml") {
        required = System.getenv("CI") != null
    }
}
