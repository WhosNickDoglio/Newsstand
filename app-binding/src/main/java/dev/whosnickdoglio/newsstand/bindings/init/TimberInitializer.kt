package dev.whosnickdoglio.newsstand.bindings.init

import com.ndoglio.core.AppScope
import com.ndoglio.core.BuildInfo
import com.ndoglio.core.BuildType
import com.ndoglio.core.Initializable
import com.ndoglio.core.isDebug
import com.squareup.anvil.annotations.ContributesMultibinding
import timber.log.Timber
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
class TimberInitializer @Inject constructor(
    private val buildInfo: BuildInfo
) : Initializable {
    override fun init() {
        if (buildInfo.isDebug()) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
