import dev.whosnickdoglio.convetions.Setup
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

plugins {
    kotlin("kapt")
}

extensions.getByType<KaptExtension>().javacOptions {
    option("--source", Setup.JAVA)
    option("--target", Setup.JAVA)
}
