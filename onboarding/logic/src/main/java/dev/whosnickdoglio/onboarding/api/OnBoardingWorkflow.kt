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

package dev.whosnickdoglio.onboarding.api

import com.squareup.workflow1.Workflow
import dev.whosnickdoglio.onboarding.api.OnBoardingWorkflow.Rendering

/**
 * [Workflow] for onboarding the user.  For a fresh user this will be the first thing they
 * see when the open the app. Right now the onboarding flow just handles allowing them to select
 * which app experience ([Feature]) they want, Feedly, InoReader or an RSS Reader.
 */
interface OnBoardingWorkflow : Workflow<Unit, Feature, Rendering> {

    /**
     *  Internal state object for the [OnBoardingWorkflow].
     *
     *  @param currentlySelectedFeature The [Feature] that user has selected, defaults to
     *  [Feature.NO_SELECTION].
     *  @param isFinished A [Boolean] that is only `true` when the user is ready to finish the
     *  onboarding flow, defaults to `false`.
     */
    data class State(
        val currentlySelectedFeature: Feature = Feature.NO_SELECTION,
        val isFinished: Boolean = false
    )

    /**
     * The output or view model for the [OnBoardingWorkflow], this class will be what the UI uses
     * to render the views.
     *
     * @param onFeatureSelected
     * @param onFinished
     * @param features
     * @param currentSelection
     */
    data class Rendering(
        val onFeatureSelected: (Feature) -> Unit = {},
        val onFinished: () -> Unit = { },
        val features: List<Feature> = listOf(Feature.FEEDLY, Feature.INO_READER, Feature.RSS),
        val currentSelection: Feature = Feature.NO_SELECTION
    )
}

