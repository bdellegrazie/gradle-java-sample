val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    pmd
}

pmd {
    incrementalAnalysis = System.getenv("CI") == null
    ruleSets = listOf("category/java/errorprone.xml", "category/java/bestpractices.xml")
    rulesMinimumPriority.set(5)
    setConsoleOutput(System.getenv("CI") == null)
    setIgnoreFailures(System.getenv("CI") != null)
    setToolVersion(versionCatalog.findVersion("pmd").get().toString())
}

tasks.withType<Pmd>().configureEach {

    reports {
        xml.required.set(System.getenv("CI") != null)
        html.required.set(System.getenv("CI") == null)
        //sarif.required.set(System.getenv("CI") != null)
    }
}
