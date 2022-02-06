import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.detekt)
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}
tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.android)
    implementation(libs.gradle.detekt)
//    implementation(libs.gradle.playpublish)
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192 Workaround for Version Catalog in plugins
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
