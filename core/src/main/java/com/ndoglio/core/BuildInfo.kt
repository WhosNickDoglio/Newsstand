package com.ndoglio.core

/**
 * Created by nicholas.doglio on 4/3/21.
 */
interface BuildInfo {

    val buildType: BuildType
}

enum class BuildType {
    DEBUG, RELEASE
}

fun BuildInfo.isDebug(): Boolean = buildType == BuildType.DEBUG
