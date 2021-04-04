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
    kotlin("kapt")
    id("com.squareup.anvil")
}

// TODO move this to setup
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.ndoglio.newsstand"
        minSdkVersion(24)
        targetSdkVersion(30)
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
        viewBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)

    implementation(project(":app-binding"))
    implementation(project(":core"))
    implementation(project(":onboarding"))
    implementation(project(":feedly:networking"))
    implementation(project(":feedly:models"))
    implementation(project(":feedly:auth"))
    implementation(project(":feedly:persistence"))
    implementation(project(":feedly:core"))
    implementation(project(":feedly:ui"))

    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)

    implementation(libs.coil)

    //Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.recyclerview)
    implementation(libs.constraint.layout)

    implementation(libs.datastore)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.common)

    implementation(libs.core.ktx)
    implementation(libs.activity)
    implementation(libs.viewpager)
    implementation(libs.swiperefreshlayout)
    implementation(libs.cardview)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.workflow.core)
    implementation(libs.workflow.android.ui)
    implementation(libs.workflow.modal)
    implementation(libs.workflow.backstack)

    implementation(libs.insetter)

    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)

    //Debugging
    implementation(libs.timber)
    //https://square.github.io/leakcanary/recipes/#uploading-to-bugsnag TODO
    debugImplementation(libs.leakcanary)

    testImplementation(libs.junit)
    testImplementation(libs.workflow.test)
    testImplementation(libs.truth)
}
