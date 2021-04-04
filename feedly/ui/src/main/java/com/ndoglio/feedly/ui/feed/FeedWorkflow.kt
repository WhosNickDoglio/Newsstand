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

package com.ndoglio.feedly.ui.feed

import com.ndoglio.core.WorkflowScreen
import com.ndoglio.feedly.ui.databinding.FeedViewBinding
import com.ndoglio.feedly.ui.feed.FeedWorkflow.Screen
import com.ndoglio.feedly.ui.feed.FeedWorkflow.State
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.ui.LayoutRunner
import com.squareup.workflow1.ui.LayoutRunner.Companion.bind
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory

internal class FeedWorkflow : StatefulWorkflow<Unit, State, Nothing, Screen>() {

    internal data class State(
        val data: List<String> = emptyList(),
        val isRefreshing: Boolean = false,
        val isLoading: Boolean = true,
    )

    internal data class Screen(
        val data: List<String>,
        val onItemClicked: () -> Unit,
        val onRefresh: () -> Unit,
        val showRefresher: Boolean,
        val showLoader: Boolean,
    ) : WorkflowScreen

    override fun initialState(props: Unit, snapshot: Snapshot?): State = State()

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): Screen {
        return Screen(
            data = renderState.data,
            onItemClicked = {},
            onRefresh = {},
            showRefresher = renderState.isRefreshing,
            showLoader = renderState.isLoading
        )
    }

    // https://github.com/square/workflow-kotlin/blob/main/samples/tictactoe/common/src/main/java/com/squareup/sample/gameworkflow/RunGameState.kt
    override fun snapshotState(state: State): Snapshot? = null
}

internal class FeedLayoutRunner(
    private val binding: FeedViewBinding
) : LayoutRunner<Screen> {
    override fun showRendering(
        rendering: Screen,
        viewEnvironment: ViewEnvironment
    ) {
        with(binding) {

        }
    }

    companion object : ViewFactory<Screen> by bind(
        FeedViewBinding::inflate, ::FeedLayoutRunner
    )
}
