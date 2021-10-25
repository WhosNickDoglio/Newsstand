buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
        classpath("com.google.gms:google-services:4.2.0")
    }
}

plugins {
    id("de.fayard.buildSrcVersions") version ("0.3.2")
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
