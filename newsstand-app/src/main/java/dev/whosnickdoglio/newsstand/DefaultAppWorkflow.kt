package dev.whosnickdoglio.newsstand

import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.WorkflowAction
import com.squareup.workflow1.action
import com.squareup.workflow1.asWorker
import com.squareup.workflow1.renderChild
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.toParcelable
import com.squareup.workflow1.ui.toSnapshot
import dagger.Lazy
import dev.whosnickdoglio.core.di.ActivityScope
import dev.whosnickdoglio.feedly.ui.FeedlyWorkflow
import dev.whosnickdoglio.onboarding.api.Feature
import dev.whosnickdoglio.onboarding.api.OnBoardingModel
import dev.whosnickdoglio.onboarding.api.OnBoardingWorkflow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import javax.inject.Inject

@ContributesBinding(ActivityScope::class, boundType = AppWorkflow::class)
class DefaultAppWorkflow @Inject constructor(
    private val onBoarding: Lazy<OnBoardingWorkflow>,
    private val feedly: Lazy<FeedlyWorkflow>,
    private val onBoardingModel: OnBoardingModel,
) : AppWorkflow, StatefulWorkflow<AppWorkflow.Props, AppWorkflow.State, Nothing, Any>() {

    override fun onPropsChanged(old: AppWorkflow.Props, new: AppWorkflow.Props, state: AppWorkflow.State): AppWorkflow.State {
        Timber.i("PROPS CHANGED $new")
        return super.onPropsChanged(old, new, state)
    }

    override fun initialState(props: AppWorkflow.Props, snapshot: Snapshot?): AppWorkflow.State =
        snapshot?.toParcelable() ?: AppWorkflow.State.OnBoarding

    override fun render(renderProps: AppWorkflow.Props, renderState: AppWorkflow.State, context: RenderContext): Any {
        context.runningWorker(
            worker = startupCheckWorker(),
            handler = { (isFinished, feature) ->
                if (isFinished) {
                    featureSelection(feature)
                } else {
                    WorkflowAction.noAction()
                }
            }
        )

        return when (renderState) {
            is AppWorkflow.State.OnBoarding -> context.renderChild(onBoarding.get()) {
                featureSelection(it)
            }
            is AppWorkflow.State.Feedly -> {
                val url = if (renderProps is AppWorkflow.Props.LoginRedirectUrl) renderProps.url else null

                context.renderChild(feedly.get(), props = FeedlyWorkflow.Props(url))
            }
            else -> error("this feature isn't supported yet.")
        }
    }

    override fun snapshotState(state: AppWorkflow.State): Snapshot = state.toSnapshot()

    private fun featureSelection(feature: Feature) = action {
        when (feature) {
            Feature.FEEDLY -> state = AppWorkflow.State.Feedly
            Feature.NO_SELECTION -> AppWorkflow.State.OnBoarding
            else -> error("This feature isn't supported yet. $feature")
        }
    }

    private fun startupCheckWorker() = combine(
        onBoardingModel.isOnBoardingFinished,
        onBoardingModel.selectedFeature
    ) { isFinished, selectedFeature -> Pair(isFinished, selectedFeature) }.asWorker()
}
