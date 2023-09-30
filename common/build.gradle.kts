plugins {
    id("java")
    id("com.github.gmazzo.buildconfig") version "4.1.2"
    `java-library`
}

group = "me.santio.common"
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("com.google.code.gson:gson:2.10.1")
    api("com.konghq:unirest-java:3.11.09")
}

buildConfig {
    buildConfigField("String", "PLUGIN_VERSION", provider { "\"${rootProject.version}\"" })
}

tasks {
    jar {
        dependsOn("generateBuildConfig")
    }
}