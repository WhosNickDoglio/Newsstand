/*
 * MIT License
 *
 * Copyright (c) 2022 Nicholas Doglio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.whosnickdoglio.newsstand.feedly.root

import android.os.Parcelable
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Workflow
import com.squareup.workflow1.ui.Screen
import com.squareup.workflow1.ui.toParcelable
import com.squareup.workflow1.ui.toSnapshot
import dev.whosnickdoglio.newsstand.anvil.AppScope
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

/** Top level entry point for the Feedly feature. */
interface FeedlyRootWorkflow :
    Workflow<FeedlyRootWorkflow.Props, Nothing, FeedlyRootWorkflow.Rendering> {

    object Props

    data class Rendering(val text: String) : Screen

    sealed interface State : Parcelable {

        @Parcelize
        object Login : State

        @Parcelize
        object Feed : State
    }
}

@ContributesBinding(scope = AppScope::class, boundType = FeedlyRootWorkflow::class)
class DefaultFeedlyRootWorkflow @Inject constructor() : FeedlyRootWorkflow,
    StatefulWorkflow<FeedlyRootWorkflow.Props, FeedlyRootWorkflow.State, Nothing, FeedlyRootWorkflow.Rendering>() {

    override fun initialState(
        props: FeedlyRootWorkflow.Props,
        snapshot: Snapshot?
    ): FeedlyRootWorkflow.State = snapshot?.toParcelable() ?: FeedlyRootWorkflow.State.Feed

    override fun render(
        renderProps: FeedlyRootWorkflow.Props,
        renderState: FeedlyRootWorkflow.State,
        context: RenderContext
    ): FeedlyRootWorkflow.Rendering = FeedlyRootWorkflow.Rendering("Hello Feedly!")

    override fun snapshotState(state: FeedlyRootWorkflow.State): Snapshot = state.toSnapshot()
}
