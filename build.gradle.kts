buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version("1.18.1")
    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
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
