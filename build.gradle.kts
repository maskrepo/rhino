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
    maven("http://packages.confluent.io/maven/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-resteasy-jsonb")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-kafka-client:$quarkusVersion")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka:$quarkusVersion")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging:$quarkusVersion")
    implementation("com.github.ben-manes.caffeine:caffeine:2.8.4")
    implementation("io.quarkus:quarkus-kafka-streams:$quarkusVersion")
    implementation("io.confluent:kafka-streams-avro-serde:5.0.0")
    implementation("io.debezium:debezium-core:1.1.2.Final")
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

allOpen {
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("javax.ws.rs.Path")
}