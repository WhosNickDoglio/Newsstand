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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.CircuitContent
import com.slack.circuit.CircuitUiEvent
import com.slack.circuit.CircuitUiState
import com.slack.circuit.Presenter
import com.slack.circuit.Screen
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.whosnickdoglio.newsstand.anvil.AppScope
import dev.whosnickdoglio.newsstand.feedly.root.FeedlyRoot
import javax.inject.Inject
import kotlinx.parcelize.Parcelize

@Parcelize
object Root : Screen {

    data class State(
        val feature: Feature,
        val eventSink: (RootEvent) -> Unit,
    ) : CircuitUiState

    enum class Feature {
        FEEDLY
    }

    sealed interface RootEvent : CircuitUiEvent
}

@CircuitInject(Root::class, AppScope::class)
class RootPresenter @Inject constructor() : Presenter<Root.State> {

    @Composable override fun present(): Root.State = Root.State(feature = Root.Feature.FEEDLY) {}
}

@CircuitInject(Root::class, AppScope::class)
@Composable
fun Root(state: Root.State, modifier: Modifier = Modifier) {
    CircuitContent(
        screen =
            when (state.feature) {
                Root.Feature.FEEDLY -> FeedlyRoot
            },
        modifier = modifier
    )
}
