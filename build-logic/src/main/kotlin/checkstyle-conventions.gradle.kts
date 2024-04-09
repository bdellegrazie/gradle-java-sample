val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    checkstyle
}

checkstyle {
    setIgnoreFailures(System.getenv("CI") != null)
    setShowViolations(System.getenv("CI") == null)
    toolVersion = versionCatalog.findVersion("checkstyle").get().toString()
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = System.getenv("CI") != null
        html.required = System.getenv("CI") == null
        sarif.required = System.getenv("CI") != null
    }
}
