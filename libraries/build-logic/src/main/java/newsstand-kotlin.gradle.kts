import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm")
    id("newsstand-detekt")
}

kotlin {
    explicitApi()
}


val newsstandExtension: NewsstandExtension = extensions.create("newsstand", NewsstandExtension::class.java)

tasks.apply {
    configureTests()
    configureJvmTarget(listOf("-Xopt-in=com.squareup.workflow1.ui.WorkflowUiExperimentalApi"))
}
