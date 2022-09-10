package dev.whosnickdoglio.newsstand

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.workflow1.ui.renderWorkflowIn
import dev.whosnickdoglio.newsstand.root.RootWorkflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import tangle.viewmodel.VMInject

class NewsstandWorkflowContainerViewModel @VMInject constructor(
    savedState: SavedStateHandle,
    rootWorkflow: RootWorkflow,
) : ViewModel() {

    private val props = MutableStateFlow(RootWorkflow.Props)

    val rendering: StateFlow<RootWorkflow.Rendering> = renderWorkflowIn(
        workflow = rootWorkflow,
        scope = viewModelScope,
        props = props,
        savedStateHandle = savedState,
    )

    fun rootProp(prop: RootWorkflow.Props) {
        props.update { prop }
    }
}
