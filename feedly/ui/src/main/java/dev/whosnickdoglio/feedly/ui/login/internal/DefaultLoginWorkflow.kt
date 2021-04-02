package dev.whosnickdoglio.feedly.ui.login.internal

import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.WorkflowAction
import com.squareup.workflow1.action
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.toParcelable
import com.squareup.workflow1.ui.toSnapshot
import dev.whosnickdoglio.auth.AuthenticationHelper
import dev.whosnickdoglio.auth.Result
import dev.whosnickdoglio.core.LaunchEvent
import dev.whosnickdoglio.core.Launcher
import dev.whosnickdoglio.core.di.ActivityScope
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow.LoggedIn
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow.Props
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow.Rendering
import dev.whosnickdoglio.feedly.ui.login.api.LoginWorkflow.State
import javax.inject.Inject

// TODO none of this works until I set up intent handling properly in MainActivity and AppWorkflow
@ContributesBinding(ActivityScope::class, boundType = LoginWorkflow::class)
class DefaultLoginWorkflow @Inject constructor(
    private val authenticationHelper: AuthenticationHelper,
    private val launcher: Launcher
) : LoginWorkflow, StatefulWorkflow<Props, State, LoggedIn, Rendering>() {

    private val isLoggedInWorker = Worker.from { authenticationHelper.isLoggedIn() }

    override fun initialState(props: Props, snapshot: Snapshot?): State =
        snapshot?.toParcelable() ?: State.LoginPrompt

    override fun snapshotState(state: State): Snapshot = state.toSnapshot()

    override fun onPropsChanged(old: Props, new: Props, state: State): State =
        if (new.codeUrl != null) {
            State.Authorizing(new.codeUrl)
        } else {
            super.onPropsChanged(old, new, state)
        }

    override fun render(
        renderProps: Props,
        renderState: State,
        context: RenderContext
    ): Rendering {
        context.runningWorker(isLoggedInWorker) { isLoggedIn -> loggedIn(isLoggedIn) }

        when (renderState) {
            is State.LaunchAuth -> {
                launcher.onEvent(LaunchEvent.OpenUrl(authenticationHelper.provideRedirectUrl()))

                return Rendering(isProcessing = true)
            }
            is State.Authorizing -> {
                context.runningWorker(
                    worker = Worker.from { authenticationHelper.retrieveTokens(renderState.code) },
                    handler = { result ->
                        when (result) {
                            Result.SUCCESS -> {
                                action {
                                    state = State.Complete
                                }
                            }
                            Result.FAILURE -> {
                                WorkflowAction.noAction()
                            }
                        }
                    }
                )

                return Rendering(isProcessing = true)
            }
            is State.Complete -> {
                loggedIn(true)
                return Rendering(isLoggedIn = true, successMessage = "Logged in!")
            }
            State.LoginPrompt -> {
                return Rendering(showPrompt = true, onLoginClicked = {
                    action {
                        state = State.LaunchAuth
                    }
                })
            }
        }
    }

    private fun loggedIn(isLoggedIn: Boolean) = action {
        if (isLoggedIn) {
            setOutput(LoggedIn)
        }
    }
}
