plugins {
    id("via.addon_subproject")
}

dependencies {
    compileOnly(projects.viaaprilfoolsCommon)
    compileOnly(libs.fabric.loader)
    compileOnly(libs.log4j.api)
}
