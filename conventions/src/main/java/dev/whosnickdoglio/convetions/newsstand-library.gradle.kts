import dev.whosnickdoglio.convetions.Setup
import gradle.kotlin.dsl.accessors._3939129eb8bc8e23f359fbdfe077b6c5.android
import gradle.kotlin.dsl.accessors._3939129eb8bc8e23f359fbdfe077b6c5.coreLibraryDesugaring
import gradle.kotlin.dsl.accessors._3939129eb8bc8e23f359fbdfe077b6c5.kotlinOptions
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("com.android.library")
    kotlin("android")
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

android {
    compileSdkVersion(Setup.COMPILE_SDK)
    defaultConfig {
        minSdkVersion(Setup.MIN_SDK)
        targetSdkVersion(Setup.TARGET_SDK)
        versionCode = Setup.VERSION_CODE
        versionName = Setup.VERSION_NAME
    }

    buildFeatures {
        buildConfig = false
        viewBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = Setup.JAVA
        targetCompatibility = Setup.JAVA
    }

    kotlinOptions {
        jvmTarget = Setup.JAVA.toString()
        useIR = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}

