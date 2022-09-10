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

package dev.whosnickdoglio.onboarding.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.whosnickdoglio.onboarding.api.Feature

// TODO clean up ripple
@Composable
internal fun SelectableFeatureRow(
    feature: Feature,
    isSelected: Boolean,
    onSelected: (Feature) -> Unit
) {
    Row(
        Modifier
            .height(56.dp)
            .selectable(
                selected = isSelected,
                onClick = { onSelected(feature) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO add feature descriptions
        RadioButton(selected = isSelected, onClick = null)
        Text(text = feature.value, modifier = Modifier.padding(8.dp))
    }
}

@Preview
@Composable
private fun UnSelectedPreviewRow() {
    Surface(color = Color.White) {
        SelectableFeatureRow(
            feature = Feature.FEEDLY,
            isSelected = false,
            onSelected = {}
        )
    }
}

@Preview
@Composable
private fun SelectedPreviewRow() {
    Surface(color = Color.White) {
        SelectableFeatureRow(
            feature = Feature.FEEDLY,
            isSelected = true,
            onSelected = {}
        )
    }
}
