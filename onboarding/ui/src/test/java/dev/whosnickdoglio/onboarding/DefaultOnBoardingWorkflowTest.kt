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

package dev.whosnickdoglio.onboarding

import dev.whosnickdoglio.onboarding.api.Feature
import dev.whosnickdoglio.onboarding.api.OnBoardingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test

class DefaultOnBoardingWorkflowTest {


    @Test
    fun `something something something`() {
        val workflow = DefaultOnBoardingWorkflow(FakeOnBoardingModel())

//        workflow.testRender()
//        workflow.launchForTestingFromStateWith()

//        workflow.launchForTestingWith()


    }

    private class FakeOnBoardingModel : OnBoardingModel {

        private val selectedFeatureFlow = MutableStateFlow(Feature.NO_SELECTION)

        private val isOnBoardingComplete = MutableStateFlow(false)

        override val selectedFeature: Flow<Feature> = selectedFeatureFlow

        override val isOnBoardingFinished: Flow<Boolean> = isOnBoardingComplete


        override suspend fun markOnBoardingComplete() {
            isOnBoardingComplete.value = true
        }

        override suspend fun selectFeature(feature: Feature) {
            selectedFeatureFlow.value = feature
        }
    }
}
