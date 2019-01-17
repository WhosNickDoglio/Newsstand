val ktlint by configurations.creating

dependencies {
    ktlint("com.github.shyiko:ktlint:0.29.0")
}

tasks.register<JavaExec>("ktlint") {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.github.shyiko.ktlint.Main"
    args("--android", "src/**/*.kt")
}

tasks.named("check") {
    dependsOn(ktlint)
}

tasks.register<JavaExec>("ktlintFormat") {
    group = "formatting"
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.github.shyiko.ktlint.Main"
    args("--android", "-F", "src/**/*.kt")
}