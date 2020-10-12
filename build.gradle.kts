val quarkusVersion: String = "1.8.0.Final"
val MaskModelVersion = "1.1.3-SNAPSHOT"
val MaskCacheVersion = "1.0.1-SNAPSHOT"
val MaskUtilVersion = "1.1.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.4.10"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    id ("io.quarkus") version "1.8.0.Final"

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
        create<MavenPublication>("rhino") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")

    implementation("io.quarkus:quarkus-kafka-client:$quarkusVersion")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka:$quarkusVersion")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging:$quarkusVersion")
    implementation("io.quarkus:quarkus-resteasy-jackson:$quarkusVersion")
    implementation("io.quarkus:quarkus-resteasy:$quarkusVersion")
    implementation("io.quarkus:quarkus-rest-client:$quarkusVersion")
    implementation("io.quarkus:quarkus-kotlin:$quarkusVersion")
    implementation("io.quarkus:quarkus-config-yaml:$quarkusVersion")

    implementation("fr.convergence.proddoc.lib:mask-model:$MaskModelVersion")
    implementation("fr.convergence.proddoc.lib:mask-util:$MaskUtilVersion")
    implementation("fr.convergence.proddoc.lib:mask-cache:$MaskCacheVersion")

    testImplementation("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.assertj:assertj-core:3.12.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
tasks.test {
    useJUnitPlatform()
}

allOpen {
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("javax.ws.rs.Path")
}

tasks.register("printVersion") {
    doLast {
        File(projectDir, "version.txt").appendText("${project.version}")
    }
}