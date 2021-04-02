/*
 * MIT License
 *
 *   Copyright (c) 2021 Nicholas Doglio
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

package dev.whosnickdoglio.newsstand.bindings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.squareup.anvil.annotations.ContributesBinding
import dev.whosnickdoglio.core.LaunchEvent
import dev.whosnickdoglio.core.Launcher
import dev.whosnickdoglio.core.di.ActivityContext
import dev.whosnickdoglio.core.di.ActivityScope
import javax.inject.Inject

/**
 * Default implementation of [Launcher] interface.
 *
 * @see Launcher
 */
@ContributesBinding(ActivityScope::class)
class DefaultLauncher @Inject constructor(
    @ActivityContext private val activity: Activity
) : Launcher {

    override fun onEvent(event: LaunchEvent) {
        when (event) {
            is LaunchEvent.OpenUrl -> openUrl(event.url)
        }
    }

    private fun openUrl(url: String) {
//        val intent = CustomTabsIntent.Builder().build()

//        intent.launchUrl(activity, Uri.parse(url))
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
