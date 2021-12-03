import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt

buildscript {
    dependencies {
        classpath(libs.gradle.android)
        classpath(libs.gradle.kotlin)
    }
}

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.ben.manes)
    alias(libs.plugins.junit.jacoco)
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.doctor)
}

junitJacoco {
    version = libs.versions.jacoco.get()
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.register<Detekt>("detektAll") {
    parallel = true
    autoCorrect = true
    config.setFrom("$projectDir/config/detekt/detekt.yml")
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
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
