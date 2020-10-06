val quarkusVersion: String = "1.5.0.Final"

plugins {
    kotlin("jvm") version "1.4.10"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"

    id ("io.quarkus") version "1.5.0.Final"

    `maven-publish`
    id ("org.sonarqube") version "2.7"
    id ("jacoco")
}

group = "fr.convergence.proddoc"
version = "1.0.0-SNAPSHOT"

val myMavenRepoUser = "myMavenRepo"
val myMavenRepoPassword ="mask"

repositories {
    mavenLocal()
    maven {
        url = uri("https://mymavenrepo.com/repo/OYRB63ZK3HSrWJfc2RIB/")
        credentials {
            username = myMavenRepoUser
            password = myMavenRepoPassword
        }
    }
    mavenCentral()
}

publishing {
    repositories {
        maven {
            url = uri("https://mymavenrepo.com/repo/ah37AFHxnt3Fln1mwTvi/")
            credentials {
                username = myMavenRepoUser
                password = myMavenRepoPassword
            }
        }
        mavenLocal()
    }

    publications {
        create<MavenPublication>("mask-model") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")

    implementation(enforcedPlatform("io.quarkus:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-kafka-client:$quarkusVersion")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka:$quarkusVersion")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging:$quarkusVersion")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")

    implementation("fr.convergence.proddoc.lib:mask-model:1.0.2-SNAPSHOT")
    implementation("fr.convergence.proddoc.lib:mask-util:1.0.2-SNAPSHOT")
    implementation("fr.convergence.proddoc.lib:mask-cache:1.0.1-SNAPSHOT")
    implementation("junit:junit:4.12")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.assertj:assertj-core:3.12.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

tasks.test {
    useJUnitPlatform()
}

allOpen {
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("javax.ws.rs.Path")
}