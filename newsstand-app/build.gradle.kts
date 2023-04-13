/*
 * MIT License
 *
 * Copyright (c) 2022 Nicholas Doglio
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
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.anvil)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.sortDependencies)
    //    alias(libs.plugins.secrets)
    id("kotlin-parcelize")
}

android {
    namespace = "dev.whosnickdoglio.newsstand"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "dev.whosnickdoglio.newsstand"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }

        buildFeatures { compose = true }

        composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions { jvmTarget = JavaVersion.VERSION_11.toString() }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)

    ksp(libs.circuitCodegen)

    lintChecks(libs.lints.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.accompanist.systems.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.circuit)
    implementation(libs.circuitCodegenAnnotations)
    implementation(libs.circuitOverlay)
    implementation(libs.circuitRetained)
    implementation(libs.compose.animations)
    implementation(libs.compose.compiler)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.dagger.core)
    implementation(projects.feedly.root)
    implementation(projects.libraries.appScope)
    implementation(projects.libraries.connectivity.impl)
    implementation(projects.libraries.coroutinesExt.impl)
    implementation(projects.libraries.design)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.leakcanary)

    kapt(libs.dagger.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
}
