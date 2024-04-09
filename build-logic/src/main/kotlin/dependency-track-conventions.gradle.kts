import com.liftric.dtcp.extensions.*
import kotlin.time.toDuration
import kotlin.time.DurationUnit

plugins {
    id("com.liftric.dependency-track-companion-plugin")
}

val version: String by project
val name: String by project
dependencyTrackCompanion {
    url = providers.environmentVariable("DTRACK_API_URL")
    apiKey = providers.environmentVariable("DTRACK_API_KEY")
    autoCreate = true
    parentUUID = providers.fileContents(rootProject.layout.projectDirectory.file(".project.uuid")).getAsText().get().trim()
    //parentName = rootProject.name
    //projectUUID = providers.fileContents(project.layout.projectDirectory.file(".project.uuid")).getAsText().get().trim()
    projectName = name
    projectVersion = version
    riskScore{
        timeout = 20.toDuration(DurationUnit.SECONDS)
        maxRiskScore = 7.0
    }
}
