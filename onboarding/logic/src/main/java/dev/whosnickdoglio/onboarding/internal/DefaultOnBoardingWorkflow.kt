package dev.whosnickdoglio.onboarding.internal

import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.WorkflowAction
import com.squareup.workflow1.action
import com.squareup.workflow1.asWorker
import com.squareup.workflow1.runningWorker
import dev.whosnickdoglio.core.di.ActivityScope
import dev.whosnickdoglio.onboarding.api.Feature
import dev.whosnickdoglio.onboarding.api.OnBoardingModel
import dev.whosnickdoglio.onboarding.api.OnBoardingWorkflow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * @see OnBoardingWorkflow
 */
@ContributesBinding(ActivityScope::class, boundType = OnBoardingWorkflow::class)
class DefaultOnBoardingWorkflow @Inject constructor(
    private val model: OnBoardingModel
) : OnBoardingWorkflow, StatefulWorkflow<Unit, OnBoardingWorkflow.State, Feature, OnBoardingWorkflow.Rendering>() {

    override fun initialState(props: Unit, snapshot: Snapshot?): OnBoardingWorkflow.State =
        OnBoardingWorkflow.State()

    override fun render(renderProps: Unit, renderState: OnBoardingWorkflow.State, context: RenderContext): OnBoardingWorkflow.Rendering {
        context.runningWorker(
            worker = startupCheckWorker(),
            handler = { (isFinished, selectedFeature) ->
                if (isFinished) {
                    output(selectedFeature)
                } else {
                    WorkflowAction.noAction()
                }
            }
        )

        context.runningWorker(
            worker = Worker.from { model.selectFeature(renderState.currentlySelectedFeature) },
            key = renderState.currentlySelectedFeature.value,
            handler = { WorkflowAction.noAction() }
        )

        if (renderState.isFinished) {
            context.runningWorker(
                worker = Worker.from { model.markOnBoardingComplete() },
                handler = { output(renderState.currentlySelectedFeature) }
            )
        }

        return OnBoardingWorkflow.Rendering(
            onFeatureSelected = { context.actionSink.send(featureSelected(it)) },
            onFinished = { context.actionSink.send(nextAction()) },
            currentSelection = renderState.currentlySelectedFeature
        )
    }

    override fun snapshotState(state: OnBoardingWorkflow.State): Snapshot? = null

    private fun startupCheckWorker() = combine(
        model.isOnBoardingFinished,
        model.selectedFeature
    ) { isFinished, selectedFeature -> Pair(isFinished, selectedFeature) }.asWorker()

    private fun featureSelected(feature: Feature) = action {
        state = state.copy(currentlySelectedFeature = feature)
    }

    private fun nextAction() = action {
        state = state.copy(isFinished = true)
    }

    private fun output(feature: Feature) = action {
        setOutput(feature)
    }
}
