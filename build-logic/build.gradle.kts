plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.android.gradle.plugin.core)
    implementation(libs.detekt.gradle)
}
