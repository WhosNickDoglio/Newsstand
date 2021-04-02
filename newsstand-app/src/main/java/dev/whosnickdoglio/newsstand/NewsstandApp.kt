package dev.whosnickdoglio.newsstand


import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewRegistry
import com.squareup.workflow1.ui.compose.WorkflowRendering
import dev.whosnickdoglio.newsstand.design.NewsstandTheme
import dev.whosnickdoglio.newsstand.extenstions.renderAsState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun NewsstandApp(
    workflow: AppWorkflow,
    propsFlow: StateFlow<AppWorkflow.Props>,
    registry: ViewRegistry,
    loggingInterceptor: LoggingInterceptor
) {
    val systemUiController = rememberSystemUiController()

    val darkIcons = NewsstandTheme.colors.isLight // TODO don't think this works as expected

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
//            darkIcons = darkIcons
            darkIcons = true
        )
    }

    val props by propsFlow.collectAsState()

    val rendering by workflow.renderAsState(
        props = props,
    )

    val viewEnvironment = remember { ViewEnvironment(mapOf(ViewRegistry to registry)) }

    WorkflowRendering(rendering, viewEnvironment)
}
