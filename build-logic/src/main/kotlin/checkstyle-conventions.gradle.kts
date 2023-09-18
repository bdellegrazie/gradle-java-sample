val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    checkstyle
}

checkstyle {
    setIgnoreFailures(System.getenv("CI") != null)
    setShowViolations(System.getenv("CI") == null)
    setToolVersion(versionCatalog.findVersion("checkstyle").get().toString())
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(System.getenv("CI") != null)
        html.required.set(System.getenv("CI") == null)
        sarif.required.set(System.getenv("CI") != null)
    }
}
