/*
 * MIT License
 *
 * Copyright (c) 2019 Nicholas Doglio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply(rootProject.file(".buildsystem/ktlint.gradle.kts"))

android {
    compileSdkVersion(App.compileSdk)
    defaultConfig {
        applicationId = "com.nicholasdoglio.newsstand"
        minSdkVersion(App.minSdk)
        targetSdkVersion(App.targetSdk)
        versionCode = App.versionCode
        versionName = App.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
            getByName("debug") {
                isTestCoverageEnabled = true
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":data"))

    //Libraries
    implementation(Libs.kotlin_stdlib_jdk8)
    implementation(Libs.paging_runtime)
    implementation(Libs.rxjava2)
    implementation(Libs.rxjava)
    implementation(Libs.rxrelay)
    implementation(Libs.rxkotlin)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerview)
    implementation(Libs.constraintlayout)
    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_common_java8)
    implementation(Libs.rxbinding_kotlin)
    implementation(Libs.rxandroid)
    implementation(Libs.autodispose_ktx)
    implementation(Libs.autodispose_android_ktx)
    implementation(Libs.autodispose_android_archcomponents_ktx)
    implementation(Libs.dagger)
    implementation(Libs.dagger_android)
    implementation(Libs.dagger_android_support)
    implementation(Libs.glide)
    implementation(Libs.epoxy)
    implementation(Libs.navigation_fragment_ktx)
    implementation(Libs.navigation_ui_ktx)
    implementation(Libs.exoplayer)
    implementation(Libs.recyclerview_integration) {
        isTransitive = false
    }

    implementation(Libs.core_ktx)
    implementation(Libs.fragment_ktx)

    implementation(Libs.threetenabp)

    implementation(Libs.converter_moshi)
    implementation(Libs.retrofit)
    implementation(Libs.adapter_rxjava2)
    implementation(Libs.retrofit_mock)
    implementation(Libs.okhttp)
    implementation(Libs.logging_interceptor)
    implementation(Libs.moshi_kotlin)
    kapt(Libs.moshi_kotlin_codegen)

    implementation(Libs.room_runtime)
    implementation(Libs.room_rxjava2)
    kapt(Libs.room_compiler)

    kapt(Libs.com_github_bumptech_glide_compiler)
    kapt(Libs.epoxy_processor)
    kapt(Libs.dagger_compiler)
    kapt(Libs.dagger_android_processor)

    //Debugging
    implementation(Libs.firebase_crash)
    implementation(Libs.timber)
    debugImplementation(Libs.leakcanary_android)
    debugImplementation(Libs.leakcanary_support_fragment)
    releaseImplementation(Libs.leakcanary_android_no_op)

    //Testing
    testImplementation(Libs.junit)
    testImplementation(Libs.kotlin_test_junit)
    testImplementation(Libs.mockk)
    androidTestImplementation(Libs.core_testing)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.room_testing)
}

//apply(plugin = "com.google.gms.google-services")
