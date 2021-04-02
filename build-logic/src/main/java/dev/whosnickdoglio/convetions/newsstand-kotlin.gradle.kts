import dev.whosnickdoglio.convetions.Setup
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    kotlin("jvm")
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = Setup.JAVA.toString()
    targetCompatibility = Setup.JAVA.toString()
}

tasks.withType<KotlinJvmCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xopt-in=com.squareup.workflow1.ui.WorkflowUiExperimentalApi", )
        jvmTarget = Setup.JAVA.toString()
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
