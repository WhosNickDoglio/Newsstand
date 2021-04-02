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

package dev.whosnickdoglio.feedly.ui.feed

import android.os.Parcelable
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Workflow
import com.squareup.workflow1.ui.toParcelable
import com.squareup.workflow1.ui.toSnapshot
import dev.whosnickdoglio.core.di.AppScope
import dev.whosnickdoglio.core.workflow.WorkflowRendering
import dev.whosnickdoglio.feedly.ui.feed.FeedWorkflow.Rendering
import dev.whosnickdoglio.feedly.ui.feed.FeedWorkflow.State
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

interface FeedWorkflow : Workflow<Unit, Nothing, Rendering> {
    // TODO move off parcelable stuff for state restoration
    @Parcelize
    data class State(
        val data: List<String> = emptyList(),
        val isRefreshing: Boolean = false,
        val isLoading: Boolean = true,
    ) : Parcelable

    data class Rendering(
        val data: List<String>,
        val onItemClicked: () -> Unit,
        val onRefresh: () -> Unit,
        val showRefresher: Boolean,
        val onSearchFabPressed: () -> Unit = {},
        val onScroll: () -> Unit = {},
        val showLoader: Boolean,
    ) : WorkflowRendering
}

@ContributesBinding(AppScope::class, boundType = FeedWorkflow::class)
class DefaultFeedWorkflow @Inject constructor() : FeedWorkflow,
    StatefulWorkflow<Unit, State, Nothing, Rendering>() {

    override fun initialState(props: Unit, snapshot: Snapshot?): State = snapshot?.toParcelable()
        ?: State()

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): Rendering = Rendering(
        data = List(30) { "Hello World" },
        onItemClicked = {},
        onRefresh = {},
        showRefresher = renderState.isRefreshing,
        showLoader = renderState.isLoading
    )

    override fun snapshotState(state: State): Snapshot = state.toSnapshot()
}
