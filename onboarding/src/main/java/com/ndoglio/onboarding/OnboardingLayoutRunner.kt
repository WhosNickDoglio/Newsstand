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

import com.ndoglio.onboarding.databinding.OnboardingViewBinding
import com.squareup.workflow1.ui.LayoutRunner
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory

class OnboardingLayoutRunner(
    private val binding: OnboardingViewBinding
) : LayoutRunner<OnboardingWorkflow.OnboardingScreen> {

    override fun showRendering(
        rendering: OnboardingWorkflow.OnboardingScreen,
        viewEnvironment: ViewEnvironment
    ) {
        with(binding) {
            nextButton.setOnClickListener { rendering.nextButtonClick() }
            onboardingRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                val featureSelected = when (checkedId) {
                    feedlyButton.id -> Feature.FEEDLY
                    inoreaderButton.id -> Feature.INOREADER
                    rssButton.id -> Feature.RSS
                    else -> error("This ID: $checkedId is not allowed!")
                }

                rendering.featureSelection(featureSelected)
            }
        }
    }

    companion object : ViewFactory<OnboardingWorkflow.OnboardingScreen> by LayoutRunner.bind(
        OnboardingViewBinding::inflate, ::OnboardingLayoutRunner
    )
}



