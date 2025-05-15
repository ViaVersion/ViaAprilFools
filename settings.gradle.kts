pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("de.florianmichael.baseproject.BaseProject") version "1.0.4"
        id("io.papermc.hangar-publish-plugin") version "0.1.3"
        id("net.raphimc.class-token-replacer") version "1.1.5"
        id("com.modrinth.minotaur") version "2.+"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "viaaprilfools"

setupViaSubproject("common")
setupViaSubproject("bukkit")
setupViaSubproject("fabric")
setupViaSubproject("sponge")
setupViaSubproject("velocity")

fun setupViaSubproject(name: String) {
    include("viaaprilfools-$name")
    project(":viaaprilfools-$name").projectDir = file(name)
}
