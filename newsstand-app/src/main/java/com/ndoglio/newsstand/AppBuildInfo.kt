package com.ndoglio.newsstand

import com.ndoglio.core.AppScope
import com.ndoglio.core.BuildInfo
import com.ndoglio.core.BuildType
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class AppBuildInfo @Inject constructor() : BuildInfo {
    override val buildType: BuildType
        get() = if (BuildConfig.DEBUG) {
            BuildType.DEBUG
        } else {
            BuildType.RELEASE
        }
}
