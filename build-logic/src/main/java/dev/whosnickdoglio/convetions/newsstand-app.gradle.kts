import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType

/*
 * MIT License
 *
 *   Copyright (c) 2021 Nicholas Doglio
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

plugins {
    id("com.android.application")
    kotlin("android")
//    id("newsstand-detekt") TODO WHY
    id("newsstand-kapt")
//    id("com.squareup.anvil")
    id("kotlin-parcelize")
//    alias(libs.plugins.play.publish)
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xopt-in=com.squareup.workflow1.ui.WorkflowUiExperimentalApi")
    }
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "dev.whosnickdoglio.newsstand"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
//        kotlinCompilerExtensionVersion = "1.1.0-alpha05"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
