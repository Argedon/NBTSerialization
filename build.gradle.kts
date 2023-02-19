plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
    id("me.champeau.jmh") version "0.6.8"
}

group = "com.argedon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    compileOnly("io.github.jglrxavpok.hephaistos:common:2.5.3")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0-RC")
    testImplementation(kotlin("test"))
    testImplementation("io.github.jglrxavpok.hephaistos:common:2.5.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0-RC")
    testImplementation("org.openjdk.jmh:jmh-core:1.36")
    testImplementation("org.openjdk.jmh:jmh-generator-annprocess:1.36")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}