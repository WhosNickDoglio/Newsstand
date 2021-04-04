package dev.whosnickdoglio.newsstand.bindings.init

import android.os.StrictMode
import com.ndoglio.core.AppScope
import com.ndoglio.core.BuildInfo
import com.ndoglio.core.BuildType
import com.ndoglio.core.Initializable
import com.ndoglio.core.isDebug
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
class StrictModeInitializer @Inject constructor(
    private val buildInfo: BuildInfo
) : Initializable {
    override fun init() {
        if (buildInfo.isDebug()) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }
}
