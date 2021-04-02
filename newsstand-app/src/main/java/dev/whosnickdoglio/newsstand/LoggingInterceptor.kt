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

package dev.whosnickdoglio.newsstand

import com.squareup.anvil.annotations.ContributesMultibinding
import com.squareup.workflow1.BaseRenderContext
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.WorkflowInterceptor
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import javax.inject.Inject

//@ContributesMultibinding
class LoggingInterceptor @Inject constructor() : WorkflowInterceptor {

    override fun <P, S> onInitialState(
        props: P,
        snapshot: Snapshot?,
        proceed: (P, Snapshot?) -> S,
        session: WorkflowInterceptor.WorkflowSession
    ): S {
        Timber.i("PROPS: $props SNAPSHOT: $snapshot SESSION: $session")
        return super.onInitialState(props, snapshot, proceed, session)
    }

    override fun <P, S> onPropsChanged(
        old: P,
        new: P,
        state: S,
        proceed: (P, P, S) -> S,
        session: WorkflowInterceptor.WorkflowSession
    ): S {
        Timber.i("OLD PROP: $old NEW PROP $new STATE $state")
        return super.onPropsChanged(old, new, state, proceed, session)
    }

    override fun <P, S, O, R> onRender(
        renderProps: P,
        renderState: S,
        context: BaseRenderContext<P, S, O>,
        proceed: (P, S, WorkflowInterceptor.RenderContextInterceptor<P, S, O>?) -> R,
        session: WorkflowInterceptor.WorkflowSession
    ): R {
        Timber.i("PROPS $renderProps STATE $renderState SESSION $session")
        return super.onRender(renderProps, renderState, context, proceed, session)
    }

    override fun onSessionStarted(
        workflowScope: CoroutineScope,
        session: WorkflowInterceptor.WorkflowSession
    ) {
        Timber.i("SESSION: $session")
        super.onSessionStarted(workflowScope, session)
    }

    override fun <S> onSnapshotState(
        state: S,
        proceed: (S) -> Snapshot?,
        session: WorkflowInterceptor.WorkflowSession
    ): Snapshot? {
        Timber.i("STATE: $state SESSION $session")
        return super.onSnapshotState(state, proceed, session)
    }
}
