import de.florianreuth.baseproject.setupViaSubprojects

pluginManagement {
    includeBuild("build-logic")

    plugins {
        // A nice no-conflict comment for patching in downgrading
    }
}

plugins {
    id("base.settings")
}

dependencyResolutionManagement {
    repositories {
        maven("https://repo.viaversion.com")
        maven("https://repo.papermc.io/repository/maven-public")
        maven("https://maven.fabricmc.net")
    }
}

rootProject.name = "viaaprilfools"

setupViaSubprojects("common", "bukkit", "fabric", "sponge", "velocity")
