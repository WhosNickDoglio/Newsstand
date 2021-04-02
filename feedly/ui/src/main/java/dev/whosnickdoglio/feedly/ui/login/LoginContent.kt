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

package dev.whosnickdoglio.feedly.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.squareup.workflow1.ui.compose.composeViewFactory
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow

// TODO do I even need this button?
//  What can I do here to improve experience?
@Composable
internal fun LoginContent(
    showPrompt: Boolean,
    onLoginClicked: () -> Unit,
    isLoading: Boolean,
    isLoginComplete: Boolean,
    successMessage: String?,
) {
    // TODO add loading spinner
    //  Also could add snackbar with confirmation
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showPrompt) {
            Button(onClick = onLoginClicked) {
                Text("Login")
            }
        } else {
            if (isLoginComplete) {
//            Snackbar()
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Button(onClick = onLoginClicked) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    Surface(color = Color.White) {
        LoginContent(
            showPrompt = false,
            onLoginClicked = {},
            isLoading = false,
            isLoginComplete = false,
            successMessage = ""
        )
    }
}

internal val loginViewFactory = composeViewFactory<LoginWorkflow.Rendering> { rendering, _ ->
    LoginContent(
        showPrompt = rendering.showPrompt,
        onLoginClicked = rendering.onLoginClicked,
        isLoading = rendering.isProcessing,
        isLoginComplete = rendering.isLoggedIn,
        successMessage = rendering.successMessage
    )
}
