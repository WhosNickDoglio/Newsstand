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

package dev.whosnickdoglio.feedly.ui.login.api

import android.os.Parcelable
import com.squareup.workflow1.Workflow
import dev.whosnickdoglio.core.workflow.WorkflowRendering
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow.LoggedIn
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow.Props
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow.Rendering
import kotlinx.parcelize.Parcelize

// TODO should this be done in a webview or chrome custom tab?
interface LoginWorkflow : Workflow<Props, LoggedIn, Rendering> {

    sealed class State : Parcelable {

        @Parcelize
        object LoginPrompt : State()

        @Parcelize
        object LaunchAuth : State()

        @Parcelize
        data class Authorizing(val code: String) : State()

        @Parcelize
        object Complete : State()
    }

    data class Props(val codeUrl: String? = null)

    object LoggedIn

    data class Rendering(
        val showPrompt: Boolean = false,
        val onLoginClicked: () -> Unit = {},
        val isProcessing: Boolean = false,
        val isLoggedIn: Boolean = false,
        val successMessage: String? = null
    ) : WorkflowRendering
}
