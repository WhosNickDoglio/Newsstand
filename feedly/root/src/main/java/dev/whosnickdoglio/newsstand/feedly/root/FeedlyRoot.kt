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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Screen
import com.slack.circuit.runtime.presenter.Presenter
import dev.whosnickdoglio.newsstand.anvil.AppScope
import javax.inject.Inject
import kotlinx.parcelize.Parcelize

@Parcelize
object FeedlyRoot : Screen {

    data class State(
        val currentScreen: FeedlyScreen,
        val eventSink: (FeedlyRootEvent) -> Unit,
    ) : CircuitUiState

    sealed interface FeedlyRootEvent : CircuitUiEvent
}

enum class FeedlyScreen {
    LOGIN,
    FEED,
}

@CircuitInject(FeedlyRoot::class, AppScope::class)
@Composable
fun FeedlyRoot(state: FeedlyRoot.State, modifier: Modifier = Modifier) {
    // TODO once I have Feedly features I can create my own backstack here
    //    val stack = rememberSaveableBackStack { push(Root) }
    //    val circuitNavigator = rememberCircuitNavigator(stack)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text("Hello Feedly! $state")
    }
}

@CircuitInject(FeedlyRoot::class, AppScope::class)
class FeedlyRootPresenter @Inject constructor() : Presenter<FeedlyRoot.State> {

    @Composable
    override fun present(): FeedlyRoot.State =
        FeedlyRoot.State(currentScreen = FeedlyScreen.LOGIN) {}
}
