package dev.whosnickdoglio.feedly.ui.login.new

import android.os.Parcelable
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Workflow
import com.squareup.workflow1.ui.toParcelable
import com.squareup.workflow1.ui.toSnapshot
import dev.whosnickdoglio.auth.AuthenticationHelper
import dev.whosnickdoglio.core.Launcher
import dev.whosnickdoglio.feedly.ui.login.new.LoginWorkflow.Output
import dev.whosnickdoglio.feedly.ui.login.new.LoginWorkflow.Props
import dev.whosnickdoglio.feedly.ui.login.new.LoginWorkflow.Rendering
import dev.whosnickdoglio.feedly.ui.login.new.LoginWorkflow.State
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

interface LoginWorkflow :
    Workflow<Props, Output, Rendering> {

    data class Props(val codeUrl: String? = null)

    sealed class State : Parcelable {
        /**
         * State to represent showing the login prompt
         */
        @Parcelize
        object Prompt : State()

        /**
         * Opening the Oauth link in a browser
         * TODO maybe Custom Tab?
         */
        @Parcelize
        object LaunchAuthFlow : State()

        /**
         * Oauth flow has returned from browser with code that can be used to get user tokens
         */
        @Parcelize
        data class Authorizing(val code: String) : State()

        /**
         * Login has been successful and we have user tokens.
         */
        @Parcelize
        object Success : State()

        /**
         * Login has failed and we have an error message.
         */
        @Parcelize
        data class Failure(val error: String) : State()
    }

    sealed class Output {
        object Success : Output()
        data class Failure(val error: String) : Output()
    }

    data class Rendering(
        val something: String
    )
}

class DefaultLoginWorkflowWorkflow @Inject constructor(
    private val authenticationHelper: AuthenticationHelper,
    private val launcher: Launcher
) : LoginWorkflow,
    StatefulWorkflow<Props, State, Output, Rendering>() {

    override fun initialState(
        props: Props,
        snapshot: Snapshot?
    ): State = snapshot?.toParcelable() ?: State.Prompt

    override fun render(
        renderProps: Props,
        renderState: State,
        context: RenderContext
    ): Rendering {
        TODO("Render")
    }

    override fun snapshotState(state: State): Snapshot = state.toSnapshot()
}
