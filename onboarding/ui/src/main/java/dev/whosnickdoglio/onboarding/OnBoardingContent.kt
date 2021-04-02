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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.squareup.workflow1.ui.compose.composeViewFactory
import dev.whosnickdoglio.newsstand.design.NewsstandTheme
import dev.whosnickdoglio.onboarding.api.Feature
import dev.whosnickdoglio.onboarding.api.OnBoardingWorkflow
import dev.whosnickdoglio.onboarding.components.SelectableFeatureRow

@Composable
internal fun OnBoardingContent(
    onFeatureSelected: (Feature) -> Unit,
    onOnBoardingFinished: () -> Unit,
    features: List<Feature>,
    currentlySelectedFeature: Feature
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Newsstand!",
            style = NewsstandTheme.typography.h5

        )
        Text(
            text = "Please select an experience.",
            style = NewsstandTheme.typography.subtitle1
        )
        Spacer(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .selectableGroup()
                .padding(8.dp)
        ) {
            features.forEach { feature ->
                SelectableFeatureRow(
                    feature = feature,
                    isSelected = feature == currentlySelectedFeature,
                    onSelected = {
                        onFeatureSelected(it)
                    }
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = { onOnBoardingFinished() },
            enabled = currentlySelectedFeature != Feature.NO_SELECTION
        ) {
            Text(text = "Next")
        }
    }
}

internal val onboardingViewFactory =
    composeViewFactory<OnBoardingWorkflow.Rendering> { rendering, environment ->
        OnBoardingContent(
            onFeatureSelected = rendering.onFeatureSelected,
            onOnBoardingFinished = rendering.onFinished,
            features = rendering.features,
            currentlySelectedFeature = rendering.currentSelection
        )
    }



@Preview
@Composable
private fun ContentPreview() {
    Surface(color = Color.White) {
        OnBoardingContent(
            onFeatureSelected = {},
            onOnBoardingFinished = {},
            features = Feature.values().filter { it != Feature.NO_SELECTION }.toList(),
            currentlySelectedFeature = Feature.RSS
        )
    }
}
