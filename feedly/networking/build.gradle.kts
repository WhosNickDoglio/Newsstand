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
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
    alias(libs.plugins.anvil)
    alias(libs.plugins.ktfmt)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.sortDependencies)
}

anvil { generateDaggerFactories.set(true) }

dependencies {
    lintChecks(libs.lints.compose)

    api(libs.coroutines.core)
    api(projects.feedly.auth)
    api(projects.feedly.models)

    implementation(libs.dagger.core)
    implementation(libs.eithernet)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.core)
    implementation(projects.libraries.appScope)
}
