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

import com.ndoglio.core.Screen
import com.ndoglio.feedly.ui.databinding.FeedViewBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.ui.LayoutRunner
import com.squareup.workflow1.ui.LayoutRunner.Companion.bind
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory

class FeedWorkflow :
    StatefulWorkflow<Unit, FeedWorkflow.FeedState, Nothing, FeedWorkflow.FeedScreen>() {

    data class FeedState(
        val data: List<String> = emptyList(),
        val isRefreshing: Boolean = false,
        val isLoading: Boolean = true,
    )

    data class FeedScreen(
        val data: List<String>,
        val onItemClicked: () -> Unit,
        val onRefresh: () -> Unit,
        val showRefresher: Boolean,
        val showLoader: Boolean,
    ) : Screen

    override fun initialState(props: Unit, snapshot: Snapshot?): FeedState = FeedState()

    override fun render(
        renderProps: Unit,
        renderState: FeedState,
        context: RenderContext
    ): FeedScreen {
        return FeedScreen(
            data = renderState.data,
            onItemClicked = {},
            onRefresh = {},
            showRefresher = renderState.isRefreshing,
            showLoader = renderState.isLoading
        )
    }

    override fun snapshotState(state: FeedState): Snapshot? = null
}

class FeedLayoutRunner(
    private val binding: FeedViewBinding
) : LayoutRunner<FeedWorkflow.FeedScreen> {
    override fun showRendering(
        rendering: FeedWorkflow.FeedScreen,
        viewEnvironment: ViewEnvironment
    ) {
        with(binding) {

        }
    }

    companion object : ViewFactory<FeedWorkflow.FeedScreen> by bind(
        FeedViewBinding::inflate, ::FeedLayoutRunner
    )
}