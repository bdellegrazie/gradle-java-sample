plugins {
    id("license-conventions")
    id("dependency-check-conventions")
    id("sonar-root-conventions")
    id("versions-conventions")
}

val pmdEnabled by extra("false")
val spotbugsEnabled by extra("false")
