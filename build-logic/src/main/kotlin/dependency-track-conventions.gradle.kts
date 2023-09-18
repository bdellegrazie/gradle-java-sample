import com.liftric.dtcp.extensions.*
import kotlin.time.toDuration
import kotlin.time.DurationUnit

plugins {
    id("com.liftric.dependency-track-companion-plugin")
}

val version: String by project
val name: String by project
dependencyTrackCompanion {
    url.set(providers.environmentVariable("DTRACK_API_URL"))
    apiKey.set(providers.environmentVariable("DTRACK_API_KEY"))
    autoCreate.set(true)
    parentUUID.set(providers.fileContents(rootProject.layout.projectDirectory.file(".project.uuid")).getAsText().get().trim())
    //parentName.set(rootProject.name)
    //projectUUID.set(providers.fileContents(project.layout.projectDirectory.file(".project.uuid")).getAsText().get().trim())
    projectName.set(name)
    projectVersion.set(version)
    riskScore{
        timeout.set(20.toDuration(DurationUnit.SECONDS))
        maxRiskScore.set(7.0)
    }
}
