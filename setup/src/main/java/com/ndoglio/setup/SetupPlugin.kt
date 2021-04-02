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

package com.ndoglio.setup

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

class SetupPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.rootProject.extensions.findByType(SetupExtension::class.java)
            ?: project.rootProject.extensions.create("setup", SetupExtension::class.java)

        project.plugins.configureEach { plugin ->
            // println(" SETUP: ${project.name} applies $plugin")
            when (plugin) {
                is AppPlugin -> {
                    project.configureJavaTasks(extension)
                }
                is LibraryPlugin -> {
                    project.configureJavaTasks(extension)
                    project.configureAndroidLibraryModule(extension)
                }
                is KotlinBasePluginWrapper -> {
                    project.configureJavaTasks(extension)
                }
                is Kapt3GradleSubplugin -> {
                    project.configureKapt(extension)
                }
            }

        }
    }

    private fun Project.configureAndroidLibraryModule(extension: SetupExtension) {
        extensions.getByType(LibraryExtension::class.java).apply {
            compileSdkVersion(extension.compileSdk.get())
            defaultConfig.apply {
                minSdkVersion(extension.minSdk.get())
                targetSdkVersion(extension.targetSdk.get())
                versionCode = extension.versionCode.get()
                versionName = extension.versionName.get()
            }

            buildFeatures.buildConfig = false
            buildFeatures.viewBinding = true

            compileOptions {
                if (extension.jvmTarget.get().isJava8) {
                    isCoreLibraryDesugaringEnabled = true
                }

                sourceCompatibility = extension.jvmTarget.get()
                targetCompatibility = extension.jvmTarget.get()
            }
        }

        dependencies.apply {
            if (extension.jvmTarget.get().isJava8) {
                add("coreLibraryDesugaring", "com.android.tools:desugar_jdk_libs:1.1.1")
            }
        }
    }

    private fun Project.configureAndroidAppModule(extension: SetupExtension) {
    }

    private fun Project.configurePureKotlinModule(extension: SetupExtension) {
    }

    private fun Project.configureKapt(extension: SetupExtension) {
        extensions.getByType(KaptExtension::class.java).apply {
            javacOptions {
                option("--source", extension.jvmTarget.get().majorVersion)
                option("--target", extension.jvmTarget.get().majorVersion)
            }
        }
    }

    private fun Project.configureJavaTasks(extension: SetupExtension) {
        tasks.withType(JavaCompile::class.java).configureEach {
            it.sourceCompatibility = extension.jvmTarget.get().toString()
            it.targetCompatibility = extension.jvmTarget.get().toString()
        }

        tasks.withType(KotlinJvmCompile::class.java).configureEach {
            it.kotlinOptions { jvmTarget = extension.jvmTarget.get().toString() }
            // it.kotlinOptions.useIR = true
        }

        tasks.withType(Test::class.java).configureEach {
            it.testLogging {
                it.exceptionFormat = TestExceptionFormat.FULL
                it.events = setOf(
                    TestLogEvent.SKIPPED,
                    TestLogEvent.PASSED,
                    TestLogEvent.FAILED
                )
                it.showStandardStreams = true
            }
        }
    }
}
