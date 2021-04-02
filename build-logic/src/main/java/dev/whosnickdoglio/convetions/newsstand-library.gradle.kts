import dev.whosnickdoglio.convetions.Setup
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("com.android.library")
    kotlin("android")
}

val catalogs = extensions.getByType<VersionCatalogsExtension>()
val libs = catalogs.named("libs")

android {
    compileSdk = Setup.COMPILE_SDK
    defaultConfig {
        minSdk = Setup.MIN_SDK
        targetSdk = Setup.TARGET_SDK
    }

    buildFeatures {
        buildConfig = false
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Setup.JAVA
        targetCompatibility = Setup.JAVA
    }

    kotlinOptions {
        freeCompilerArgs += listOf("-Xopt-in=com.squareup.workflow1.ui.WorkflowUiExperimentalApi")
        jvmTarget = Setup.JAVA.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.4"
    }
}

tasks.withType<Test>().configureEach {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(
            TestLogEvent.SKIPPED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED
        )
        showStandardStreams = true
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = Setup.JAVA.toString()
    targetCompatibility = Setup.JAVA.toString()
}

pluginManager.withPlugin("com.android.library") {
    dependencies.addProvider("coreLibraryDesugaring", libs.findDependency("desugar").get())
}
