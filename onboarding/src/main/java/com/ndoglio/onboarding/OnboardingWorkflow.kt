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

import com.ndoglio.core.Screen
import com.ndoglio.onboarding.OnboardingWorkflow.OnboardingOutput
import com.ndoglio.onboarding.OnboardingWorkflow.OnboardingScreen
import com.ndoglio.onboarding.OnboardingWorkflow.OnboardingState
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.action
import com.squareup.workflow1.runningWorker
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class OnboardingWorkflow @Inject constructor(
    model: OnboardingModel
) : StatefulWorkflow<Unit, OnboardingState, OnboardingOutput, OnboardingScreen>() {
    // TODO can this be stateless?

    data class OnboardingOutput(
        val selectedFeature: Feature
    )

    data class OnboardingState(
        val selectedFeature: Feature? = null
    )

    data class OnboardingScreen(
        val featureSelection: (Feature) -> Unit = {},
        val nextButtonClick: () -> Unit = {}
    ) : Screen

    private val isOnboardingCompleteWorker = model.asWorker()

    override fun initialState(props: Unit, snapshot: Snapshot?): OnboardingState =
        OnboardingState()

    override fun render(
        renderProps: Unit,
        renderState: OnboardingState,
        context: RenderContext
    ): OnboardingScreen {
        context.runningWorker(
            isOnboardingCompleteWorker,
            handler = { _ -> onNextButtonClicked(renderState) })

        return OnboardingScreen(
            featureSelection = { context.actionSink.send(onFeatureSelected(it)) },
            nextButtonClick = {
                context.actionSink.send(onNextButtonClicked((renderState)))
                // context.runningSideEffect("ONBOARDING_COMPLETE") { model.markOnboardingComplete() }
            }
        )
    }

    // TODO what does snapshot support look like?
    override fun snapshotState(state: OnboardingState): Snapshot? = null

    private fun onFeatureSelected(feature: Feature) = action {
        Timber.i("FEATURE SELECTED $feature")
        state = state.copy(selectedFeature = feature)
    }

    private fun onNextButtonClicked(state: OnboardingState) = action {
        setOutput(
            OnboardingOutput(
                state.selectedFeature ?: error("Must have at least one selected feature!")
            )
        )
    }
}

private class OnboardingIsCompleteWorker(
    private val model: OnboardingModel
) : Worker<Boolean> {
    override fun run(): Flow<Boolean> = model.onboardingIsComplete
}

private fun OnboardingModel.asWorker(): OnboardingIsCompleteWorker =
    OnboardingIsCompleteWorker(this)

enum class Feature { FEEDLY, INOREADER, RSS }