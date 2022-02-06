import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

/**
 * Configures [JavaCompile] and [KotlinJvmCompile] tasks so that they are compiling and
 * targeting the [JAVA_VERSION] constant and adds any [compilerArgs] provided.
 */
fun TaskContainer.configureJvmTarget(compilerArgs: List<String> = emptyList()) {
    apply {
        withType<JavaCompile>().configureEach {
            sourceCompatibility = JAVA_VERSION.toString()
            targetCompatibility = JAVA_VERSION.toString()
        }
        withType<KotlinJvmCompile>().configureEach {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + compilerArgs
                jvmTarget = JAVA_VERSION.toString()

            }
        }
    }
}

/**
 *
 */
fun TaskContainer.configureTests() {
    withType<Test>().configureEach {
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = setOf(
                TestLogEvent.SKIPPED, TestLogEvent.PASSED, TestLogEvent.FAILED
            )
            showStandardStreams = true
        }
    }
}
