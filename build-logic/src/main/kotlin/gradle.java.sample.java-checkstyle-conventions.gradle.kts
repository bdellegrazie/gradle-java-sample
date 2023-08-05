val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    checkstyle
}

checkstyle {
    toolVersion = versionCatalog.findVersion("checkstyle").get().toString()
}

tasks.withType<Checkstyle>().configureEach {
    ignoreFailures = System.getenv("CI") != null
    reports {
        xml.required.set(System.getenv("CI") != null)
        html.required.set(System.getenv("CI") == null)
        sarif.required.set(System.getenv("CI") != null)
    }
}
