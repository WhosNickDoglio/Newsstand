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
//    id("newsstand-detekt")
    id("newsstand-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.anvil)
    alias(libs.plugins.play.publish)
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
        versionName = "0.1.0"
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
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)

    implementation(projects.core)
    implementation(projects.design)
//    implementation(projects.startup)
    implementation(projects.appBinding)
    implementation(projects.onboarding.ui)
    implementation(projects.feedly.networking)
    implementation(projects.feedly.models)
    implementation(projects.feedly.auth)
    implementation(projects.feedly.core)
    implementation(projects.feedly.ui)

    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)

    implementation(libs.coil)

    implementation(libs.startup)

    implementation(libs.compose.ui.core)
    implementation(libs.compose.ui.tooling.core)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animations)
    implementation(libs.compose.compiler)
    implementation(libs.compose.material.core)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.activity.ext)
    implementation(libs.compose.constraintlayout)

    implementation(libs.accompanist.insets.core)
    implementation(libs.accompanist.insets.ui)
    implementation(libs.accompanist.systems.ui)

    implementation(libs.datastore)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.workflow.core)
    implementation(libs.workflow.android.ui)
    implementation(libs.workflow.container)

    implementation(libs.workflow.ui.compose)
    implementation(libs.workflow.ui.compose.tooling)


    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)

    //Debugging
    implementation(libs.timber)
    //https://square.github.io/leakcanary/recipes/#uploading-to-bugsnag TODO
    debugImplementation(libs.leakcanary)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.workflow.test)
}
