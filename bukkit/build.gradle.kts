plugins {
    id("via.addon_subproject")
}

dependencies {
    compileOnly(projects.viaaprilfoolsCommon)
    compileOnly(libs.spigot.api)
}
