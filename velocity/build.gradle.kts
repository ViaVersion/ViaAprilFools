plugins {
    id("via.addon_subproject")
}

dependencies {
    compileOnly(projects.viaaprilfoolsCommon)
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
}
