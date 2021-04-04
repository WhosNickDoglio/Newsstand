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

package com.ndoglio.onboarding

import com.ndoglio.core.WorkflowScreen
import com.ndoglio.onboarding.OnboardingWorkflow.Output
import com.ndoglio.onboarding.OnboardingWorkflow.Screen
import com.ndoglio.onboarding.OnboardingWorkflow.State
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.asWorker
import com.squareup.workflow1.runningWorker
import timber.log.Timber
import javax.inject.Inject

class OnboardingWorkflow @Inject constructor(
    private val model: OnboardingModel
) : StatefulWorkflow<Unit, State, Output, Screen>() {
    // TODO can this be stateless?

    data class Output(val selectedFeature: Feature)

    data class State(val selectedFeature: Feature? = null)

    data class Screen(
        val featureSelection: (Feature) -> Unit = {},
        val nextButtonClick: () -> Unit = {}
    ) : WorkflowScreen

    override fun initialState(props: Unit, snapshot: Snapshot?): State = State()

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): Screen {
        context.runningWorker(
            model.onboardingIsComplete.asWorker(),
            handler = { _ -> onNextButtonClicked(renderState) })

        return Screen(
            featureSelection = { context.actionSink.send(onFeatureSelected(it)) },
            nextButtonClick = {
                context.actionSink.send(onNextButtonClicked((renderState)))
                context.runningSideEffect("ONBOARDING_COMPLETE") {
                    model.markOnboardingComplete()
                }
            }
        )
    }

    // TODO what does snapshot support look like?
    override fun snapshotState(state: State): Snapshot? = null

    private fun onFeatureSelected(feature: Feature) = action {
        Timber.d("FEATURE SELECTED $feature")
        state = state.copy(selectedFeature = feature)
    }

    private fun onNextButtonClicked(state: State) = action {
        setOutput(
            Output(
                state.selectedFeature ?: error("Must have at least one selected feature!")
            )
        )
    }
}

enum class Feature { FEEDLY, INOREADER, RSS }
