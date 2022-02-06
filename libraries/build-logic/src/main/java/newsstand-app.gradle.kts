@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.accessors.dm.LibrariesForLibs

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
    id("newsstand-detekt")
//    alias(libs.plugins.play.publish)
}
val libs = the<LibrariesForLibs>()

tasks.apply {
    configureTests()
    configureJvmTarget(listOf("-Xopt-in=com.squareup.workflow1.ui.WorkflowUiExperimentalApi"))
}

extensions.configure<ApplicationExtension> {
    compileSdk = COMPILE_SDK
    defaultConfig {
        applicationId = "dev.whosnickdoglio.newsstand"
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK
        versionCode = VERSION_CODE
        versionName = VERSION_NAME
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies.addProvider("coreLibraryDesugaring", libs.desugar)
