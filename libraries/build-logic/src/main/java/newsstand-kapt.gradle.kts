import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

pluginManager.withPlugin("org.jetbrains.kotlin.android") {
    applyKapt()
}

pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
    applyKapt()
}

fun applyKapt() {
    plugins {
        kotlin("kapt")
    }

    extensions.getByType<KaptExtension>().javacOptions {
        option("--source", JAVA_VERSION)
        option("--target", JAVA_VERSION)
    }
}
