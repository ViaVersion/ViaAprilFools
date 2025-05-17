dependencies {
    compileOnly(project(":viaaprilfools-common"))
    compileOnly("com.velocitypowered:velocity-api:3.1.1")?.also { annotationProcessor(it) }
}
