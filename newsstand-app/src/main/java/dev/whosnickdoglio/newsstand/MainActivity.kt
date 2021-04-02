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

package dev.whosnickdoglio.newsstand

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.squareup.workflow1.ui.ViewRegistry
import com.squareup.workflow1.ui.backstack.BackStackContainer
import com.squareup.workflow1.ui.plus
import dev.whosnickdoglio.feedly.ui.feedlyViewRegistry
import dev.whosnickdoglio.newsstand.design.NewsstandTheme
import dev.whosnickdoglio.newsstand.di.ActivityComponent
import dev.whosnickdoglio.newsstand.di.injector
import dev.whosnickdoglio.onboarding.onboardingViewRegistry
import javax.inject.Inject

/**
 * The sole [ComponentActivity] for the app.  Acts as our entry point
 * and renders our top level [AppWorkflow].
 *  TODO more
 */
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var appWorkflow: AppWorkflow

    @Inject
    internal lateinit var intentHandler: IntentHandler

    @Inject
    internal lateinit var loggingInterceptor: LoggingInterceptor

    private val viewRegistry =
        ViewRegistry(BackStackContainer) + onboardingViewRegistry + feedlyViewRegistry

    private val component: ActivityComponent by lazy {
        injector.activityComponentFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        intentHandler.intent(intent)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NewsstandTheme {
                ProvideWindowInsets {
                    NewsstandApp(
                        workflow = appWorkflow,
                        propsFlow = intentHandler.props,
                        registry = viewRegistry,
                        loggingInterceptor = loggingInterceptor
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) intentHandler.intent(intent)
    }
}
