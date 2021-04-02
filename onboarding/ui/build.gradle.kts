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
    id("newsstand-library")
    id("newsstand-detekt")
    alias(libs.plugins.anvil)
    id("kotlin-parcelize")
}

anvil {
    generateDaggerFactories.set(true)
}

dependencies {
    api(projects.onboarding.logic) // TODO these feels like a code smell
    implementation(projects.core)
//    implementation(projects.coreWorkflow)
    implementation(projects.design)

    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)

    implementation(libs.coil)

    implementation(libs.datastore)

    implementation(libs.moshi.core)
    implementation(libs.moshi.adapters)

    implementation(libs.compose.ui.core)
    implementation(libs.compose.ui.tooling.core)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animations)
    implementation(libs.compose.compiler)
    implementation(libs.compose.material.core)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)

    implementation(libs.accompanist.pager.core)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.workflow.core)
    implementation(libs.workflow.android.ui)
    implementation(libs.workflow.container)

    implementation(libs.workflow.ui.compose)
    implementation(libs.workflow.ui.compose.tooling)

    implementation(libs.dagger.core)

    //Debugging
    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.workflow.test)

}
