import dev.whosnickdoglio.convetions.Setup
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
        option("--source", Setup.JAVA)
        option("--target", Setup.JAVA)
    }
}
