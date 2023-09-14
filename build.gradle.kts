import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.santio"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":paper"))
}

tasks {
    withType<ShadowJar> {
        archiveClassifier.set("")
        archiveVersion.set("")
        archiveBaseName.set("MinehutVotes")
    }
}