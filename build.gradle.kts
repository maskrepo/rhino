val quarkusVersion: String = "1.5.0.Final"

plugins {
    kotlin("jvm") version "1.3.70"
    id ("io.quarkus") version "1.5.0.Final"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.70"
    // pour ce plugin avro dernière version ici :  https://github.com/davidmc24/gradle-avro-plugin/releases
    id ("com.commercehub.gradle.plugin.avro") version "0.20.0"
}

group = "fr.convergence.proddoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    testImplementation("io.quarkus:quarkus-junit5")
    implementation("io.quarkus:quarkus-kafka-client:1.5.0.Final")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging:1.5.0.Final")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka:1.5.0.Final")
    implementation("com.github.ben-manes.caffeine:caffeine:2.8.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0") // JVM dependency
    implementation("org.apache.avro:avro:1.9.2")
}

dependencies {
    allOpen {
        annotation("javax.enterprise.context.ApplicationScoped")
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}