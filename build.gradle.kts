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

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath(kotlin("gradle-plugin", version = "1.4.32"))
        classpath("com.squareup.sqldelight:gradle-plugin:1.4.4")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version ("1.16.0")
    id("com.github.ben-manes.versions").version("0.38.0")
    id("com.osacky.doctor") version ("0.7.0")
    // id("com.autonomousapps.dependency-analysis") version ("0.71.0")
    id("com.squareup.anvil") version ("2.2.0") apply false
    id("com.ndoglio.setup")
}

setup {
    jvmTarget.set(JavaVersion.VERSION_1_8)
    compileSdk.set(30)
    minSdk.set(23)
    targetSdk.set(30)
    versionCode.set(1)
    versionName.set("1.0.0")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "com.ndoglio.setup")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    // dependencies {
    //     detektPlugins(libs.detekt.formatting)
    // }

    // Address https://github.com/gradle/gradle/issues/4823: Force parent project evaluation before sub-project evaluation for Kotlin build scripts
    if (gradle.startParameter.isConfigureOnDemand
        && buildscript.sourceFile?.extension?.toLowerCase() == "kts"
        && parent != rootProject
    ) {
        generateSequence(parent) { project -> project.parent.takeIf { it != rootProject } }
            .forEach { evaluationDependsOn(it.path) }
    }

    tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile::class.java).configureEach {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-Xopt-in=com.squareup.workflow1.ui.WorkflowUiExperimentalApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektAll") {
    parallel = true
    autoCorrect = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    buildUponDefaultConfig = false
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}

fun isNonStable(version: String): Boolean {
    val unstableKeywords =
        listOf("ALPHA", "RC", "BETA", "DEV", "-M").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return unstableKeywords && !regex.matches(version)
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
