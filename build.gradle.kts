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

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.ncorti.ktfmt.gradle.KtfmtExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.anvil) apply false
    alias(libs.plugins.ktfmt) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.sortDependencies) apply false
    alias(libs.plugins.gradle.versions)
//    alias(libs.plugins.junit.jacoco)
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.doctor)
}

subprojects {
    // TODO fix
    pluginManager.withPlugin("com.ncorti.ktfmt.gradle") {
        configure<KtfmtExtension> { kotlinLangStyle() }
    }

    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        configure<KotlinJvmProjectExtension> { jvmToolchain(11) }
    }

    pluginManager.withPlugin("org.jetbrains.kotlin.android") {
        configure<KotlinAndroidProjectExtension> { jvmToolchain(11) }
    }
}

//junitJacoco { version = libs.versions.jacoco.get() }

tasks.register<Delete>("clean") { delete(rootProject.buildDir) }

fun isNonStable(version: String): Boolean {
    val unstableKeywords =
        listOf("ALPHA", "RC", "BETA", "DEV", "-M").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return unstableKeywords && !regex.matches(version)
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    rejectVersionIf { isNonStable(candidate.version) }
}
