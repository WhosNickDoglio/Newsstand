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

package com.ndoglio.feedly.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.ndoglio.auth.AuthenticationHelper
import com.ndoglio.core.WorkflowScreen
import com.ndoglio.feedly.ui.databinding.LoginViewBinding
import com.ndoglio.feedly.ui.login.LoginWorkflow.LoggedIn
import com.ndoglio.feedly.ui.login.LoginWorkflow.Screen
import com.ndoglio.feedly.ui.login.LoginWorkflow.Props
import com.ndoglio.feedly.ui.login.LoginWorkflow.State
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.action
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.LayoutRunner
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory
import javax.inject.Inject

class LoginWorkflow @Inject constructor(
    private val authenticationHelper: AuthenticationHelper
) : StatefulWorkflow<Props, State, LoggedIn, Screen>() {

    sealed class State {
        object LoginPrompt : State()
        object Oauth : State()
        object RetrievingTokens : State()
    }

    data class Props(
        val codeUrl: String? = null,
        val context: Context? = null
    )

    data class LoginModel(
        val redirectUrl: String,
        val isLoggedIn: Boolean = false
    )

    object LoggedIn

    data class Screen(
        val onLoginSelected: () -> Unit
    ) : WorkflowScreen

    override fun initialState(props: Props, snapshot: Snapshot?): State = State.LoginPrompt

    override fun render(
        renderProps: Props,
        renderState: State,
        context: RenderContext
    ): Screen {

        // when (renderState) {
        //     State.LoginPrompt -> TODO()
        //     State.Oauth -> TODO()
        //     State.RetrievingTokens -> TODO()
        // }
        //
        //
        // context.runningWorker(Worker.from { authenticationHelper.isLoggedIn() }, handler = {
        //     loggedIn(it)
        // })
        //
        // renderProps.codeUrl?.let { code ->
        //     context.runningSideEffect("RETRIEVE_TOKENS") {
        //         authenticationHelper.retrieveTokens(code)
        //     }
        // }
        //
        // if (renderState.isLoggedIn) {
        //     context.actionSink.send(onLoggedIn())
        // }

        return Screen(
            onLoginSelected = { renderProps.context?.let { launchRedirectUrl(it) } }
        )
    }

    override fun snapshotState(state: State): Snapshot? = null

    private fun launchRedirectUrl(context: Context) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(authenticationHelper.provideRedirectUrl())
            )
        )
    }

    private fun onLoggedIn() = action {
        setOutput(LoggedIn)
    }
}

class LoginLayoutRunner(
    private val binding: LoginViewBinding
) : LayoutRunner<Screen> {

    override fun showRendering(rendering: Screen, viewEnvironment: ViewEnvironment) {
        with(binding) {

            logIn.setOnClickListener {
                rendering.onLoginSelected()
            }
        }
    }

    companion object : ViewFactory<Screen> by LayoutRunner.bind(
        LoginViewBinding::inflate, ::LoginLayoutRunner
    )
}
