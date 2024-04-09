val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    pmd
}

pmd {
    incrementalAnalysis = System.getenv("CI") == null
    ruleSets = listOf("category/java/errorprone.xml", "category/java/bestpractices.xml")
    rulesMinimumPriority = 4
    setConsoleOutput(System.getenv("CI") == null)
    setIgnoreFailures(System.getenv("CI") != null)
    toolVersion = versionCatalog.findVersion("pmd").get().toString()
}

tasks.withType<Pmd>().configureEach {
    enabled = (findProperty("pmdEnabled") as String).toBoolean()
    reports {
        xml.required = System.getenv("CI") != null
        html.required = System.getenv("CI") == null
        //sarif.required = System.getenv("CI") != null
    }
}
