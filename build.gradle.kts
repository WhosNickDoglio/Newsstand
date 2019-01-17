// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.0")
        classpath(kotlin("gradle-plugin", version = "1.3.11"))
        classpath("com.vanniktech:gradle-android-junit-jacoco-plugin:0.13.0")
        classpath("com.google.gms:google-services:4.2.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version ("1.0.0-RC12")
    id("de.fayard.buildSrcVersions") version ("0.3.2")
    id("com.vanniktech.android.junit.jacoco") version ("0.13.0")
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}