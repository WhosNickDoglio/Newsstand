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

package dev.whosnickdoglio.feedly.ui

import android.os.Parcelable
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.Workflow
import com.squareup.workflow1.action
import com.squareup.workflow1.renderChild
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.toParcelable
import com.squareup.workflow1.ui.toSnapshot
import dagger.Lazy
import dev.whosnickdoglio.auth.AuthenticationHelper
import dev.whosnickdoglio.core.di.ActivityScope
import dev.whosnickdoglio.core.workflow.WorkflowRendering
import dev.whosnickdoglio.feedly.ui.FeedlyWorkflow.Props
import dev.whosnickdoglio.feedly.ui.FeedlyWorkflow.State
import dev.whosnickdoglio.feedly.ui.feed.FeedWorkflow
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import javax.inject.Inject

interface FeedlyWorkflow : Workflow<Props, Nothing, WorkflowRendering> {

    data class Props(val codeUrl: String? = null)

    sealed class State : Parcelable {
        @Parcelize
        object Login : State()

        @Parcelize
        object Home : State()

        @Parcelize
        object ManageFeeds : State()

        @Parcelize
        object HomeMenu : State()

        @Parcelize
        object Settings : State()

        @Parcelize
        object Search : State()
    }
}

@ContributesBinding(ActivityScope::class, boundType = FeedlyWorkflow::class)
class DefaultFeedlyWorkflow @Inject constructor(
    private val feedWorkflow: Lazy<FeedWorkflow>,
    private val loginWorkflow: Lazy<LoginWorkflow>,
    private val authenticationHelper: AuthenticationHelper
) : FeedlyWorkflow, StatefulWorkflow<Props, State, Nothing, WorkflowRendering>() {

    override fun onPropsChanged(old: Props, new: Props, state: State): State {
        Timber.i("PROPS CHANGED $new")
        return super.onPropsChanged(old, new, state)
    }

    override fun initialState(props: Props, snapshot: Snapshot?): State =
        snapshot?.toParcelable() ?: State.Login

    override fun render(
        renderProps: Props,
        renderState: State,
        context: RenderContext
    ): WorkflowRendering {
        context.runningWorker(
            Worker.from { authenticationHelper.isLoggedIn() },
            handler = { showLoginAction() }
        )
        return when (renderState) {
            is State.Login -> context.renderChild(
                child = loginWorkflow.get(),
                props = LoginWorkflow.Props(renderProps.codeUrl)
            ) { loggedInAction() }
            is State.Home -> context.renderChild(feedWorkflow.get())
            else -> error("")
        }
    }

    override fun snapshotState(state: State): Snapshot = state.toSnapshot()

    private fun showLoginAction() = action {
        state = State.Login
    }

    private fun loggedInAction() = action {
        state = State.Home
    }
}
