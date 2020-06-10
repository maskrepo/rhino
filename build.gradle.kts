val quarkusVersion: String = "1.5.0.Final"

plugins {
    kotlin("jvm") version "1.3.61"
    id ("io.quarkus") version "1.5.0.Final"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.3.72"
}

group = "fr.convergence.proddoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    testImplementation("io.quarkus:quarkus-junit5")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}