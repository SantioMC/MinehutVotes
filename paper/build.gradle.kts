plugins {
    id("java")
}

group = "me.santio.paper"
version = rootProject.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.13-R0.1-SNAPSHOT")
    implementation(project(":common"))
}