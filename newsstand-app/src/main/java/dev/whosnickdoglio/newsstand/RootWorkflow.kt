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

package dev.whosnickdoglio.newsstand

import android.os.Parcelable
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Workflow
import com.squareup.workflow1.renderChild
import com.squareup.workflow1.ui.Screen
import com.squareup.workflow1.ui.toParcelable
import com.squareup.workflow1.ui.toSnapshot
import dev.whosnickdoglio.newsstand.anvil.AppScope
import dev.whosnickdoglio.newsstand.feedly.root.FeedlyRootWorkflow
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

// TODO do we want to separate this out to a JVM module? or a public vs impl setup?
/**
 * Top-level root/parent [Workflow] for the entire app. This is the entry
 * point.
 */
interface RootWorkflow : Workflow<RootWorkflow.Props, Nothing, Screen> {

    object Props

    sealed interface State : Parcelable {

        @Parcelize
        object Feedly : State
    }
}

@ContributesBinding(scope = AppScope::class, boundType = RootWorkflow::class)
class DefaultRootWorkflow @Inject constructor(
    private val feedlyRootWorkflow: FeedlyRootWorkflow
) : RootWorkflow,
    StatefulWorkflow<RootWorkflow.Props, RootWorkflow.State, Nothing, Screen>() {

    override fun initialState(props: RootWorkflow.Props, snapshot: Snapshot?): RootWorkflow.State =
        snapshot?.toParcelable() ?: RootWorkflow.State.Feedly

    override fun render(
        renderProps: RootWorkflow.Props,
        renderState: RootWorkflow.State,
        context: RenderContext
    ): Screen = context.renderChild(
        child = feedlyRootWorkflow,
        props = FeedlyRootWorkflow.Props
    )

    override fun snapshotState(state: RootWorkflow.State): Snapshot = state.toSnapshot()
}
