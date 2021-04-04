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

package com.ndoglio.newsstand

import android.content.Context
import android.content.Intent
import com.ndoglio.core.WorkflowScreen
import com.ndoglio.feedly.ui.FeedlyWorkflow
import com.ndoglio.feedly.ui.feedlyInjector
import com.ndoglio.newsstand.AppWorkflow.Props
import com.ndoglio.newsstand.AppWorkflow.State
import com.ndoglio.onboarding.Feature
import com.ndoglio.onboarding.OnboardingWorkflow
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.renderChild
import javax.inject.Inject

class AppWorkflow @Inject constructor(
    private val feedly: FeedlyWorkflow,
    private val onboardingWorkflow: OnboardingWorkflow
) : StatefulWorkflow<Props, State, Nothing, WorkflowScreen>() {

    data class Props(
        val intent: Intent? = null,
        val context: Context? = null,
    )

    sealed class State {
        object Onboarding : State()
        object Feedly : State()
        object Rss : State()
        object Inoreader : State()
    }

    override fun initialState(props: Props, snapshot: Snapshot?): State = State.Feedly

    override fun render(
        renderProps: Props,
        renderState: State,
        context: RenderContext
    ): WorkflowScreen = when (renderState) {
        State.Feedly -> {
            renderProps.context?.feedlyInjector()
            val code =
                if (renderProps.intent?.data != null) renderProps.intent.data.toString() else null

            val feedly =
                context.renderChild(feedly, FeedlyWorkflow.Props(code, renderProps.context))

            feedly
        }
        State.Onboarding -> context.renderChild(onboardingWorkflow, Unit) { output ->
            onboardingComplete(output.selectedFeature)
        }
        else -> error("THIS STATE: $renderState NOT SUPPORTED YET")
    }

    override fun snapshotState(state: State): Snapshot? = null

    private fun onboardingComplete(feature: Feature) = action {
        state = when (feature) {
            Feature.FEEDLY -> State.Feedly
            else -> error("Not yet supported.")
        }
    }

    private fun onLoggedIn() = action {
        // TODO need to handle other features
        state = State.Feedly
    }
}

