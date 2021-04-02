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
import com.ndoglio.core.Screen
import com.ndoglio.feedly.ui.databinding.LoginViewBinding
import com.ndoglio.feedly.ui.login.LoginWorkflow.LoginProps
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.action
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.LayoutRunner
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWorkflow @Inject constructor(private val authenticationHelper: AuthenticationHelper) :
    StatefulWorkflow<LoginProps, LoginModel, LoggedIn, LoginScreen>() {

    data class LoginProps(
        val codeUrl: String? = null,
        val context: Context? = null
    )

    override fun initialState(props: LoginProps, snapshot: Snapshot?): LoginModel =
        LoginModel(
            redirectUrl = authenticationHelper.provideRedirectUrl(),
            isLoggedIn = authenticationHelper.isLoggedIn
        )

    override fun render(
        renderProps: LoginProps,
        renderState: LoginModel,
        context: RenderContext
    ): LoginScreen {
        renderProps.codeUrl?.let { context.runningWorker(authenticationHelper.asTokenWorker(it)) }

        if (renderState.isLoggedIn) {
            context.actionSink.send(onLoggedIn())
        }

        return LoginScreen(
            onLoginSelected = { renderProps.context?.let { launchRedirectUrl(it) } }
        )
    }

    override fun snapshotState(state: LoginModel): Snapshot? = null

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

data class LoginModel(
    val redirectUrl: String,
    val isLoggedIn: Boolean = false
)

object LoggedIn

class LoginLayoutRunner(
    private val binding: LoginViewBinding
) : LayoutRunner<LoginScreen> {

    override fun showRendering(rendering: LoginScreen, viewEnvironment: ViewEnvironment) {
        with(binding) {

            logIn.setOnClickListener {
                rendering.onLoginSelected()
            }
        }
    }

    companion object : ViewFactory<LoginScreen> by LayoutRunner.bind(
        LoginViewBinding::inflate, ::LoginLayoutRunner
    )
}

data class LoginScreen(
    val onLoginSelected: () -> Unit
) : Screen

private class RetrieveTokensWorker(
    val authCode: String,
    val authenticationHelper: AuthenticationHelper
) : Worker<Nothing> {
    override fun run(): Flow<Nothing> {
        return flow {
            authenticationHelper.retrieveTokens(authCode)
        }
    }
}

private fun AuthenticationHelper.asTokenWorker(authCode: String): Worker<Nothing> =
    RetrieveTokensWorker(authCode, this)