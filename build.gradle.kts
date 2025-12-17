val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.2.21"
    id("io.ktor.plugin") version "3.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.21"
}

group = "com.hub"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation("org.openfolder:kotlin-asyncapi-ktor:3.1.3")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-jackson")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-serialization-gson")
    implementation("io.ktor:ktor-server-di")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.github.damirdenis-tudor:ktor-server-rabbitmq:1.3.6")
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("com.h2database:h2:2.3.232")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("io.github.davidepianca98:kmqtt-common-jvm:1.0.0")
    implementation("io.github.davidepianca98:kmqtt-client-jvm:1.0.0")
}
