import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.android.library")
    kotlin("android")
    id("newsstand-detekt")
}

val libs = the<LibrariesForLibs>()

val newsstand = extensions.create("newsstand", NewsstandExtension::class.java)

kotlin {
    explicitApi()
}

extensions.configure<LibraryExtension>() {
    compileSdk = COMPILE_SDK
    defaultConfig {
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

/*
   Without the `afterEvaluate` any downstream project `newsstand` configuration would just be ignored since the
   `android` block would have been evaluated  first.
 */
afterEvaluate {
    extensions.configure<LibraryExtension>() {
        buildFeatures {
            buildConfig = newsstand.isBuildConfigEnabled.get()
            compose = newsstand.isComposeEnabled.get()
        }

        if (newsstand.isComposeEnabled.get()) {
            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.compose.get()
            }
        }
    }
}

dependencies.addProvider("coreLibraryDesugaring", libs.desugar)

tasks.apply {
    configureTests()
    configureJvmTarget(listOf("-Xopt-in=com.squareup.workflow1.ui.WorkflowUiExperimentalApi"))
}
