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

package dev.whosnickdoglio.newsstand.binding.connectivity

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import com.squareup.anvil.annotations.ContributesBinding
import dev.whosnickdoglio.newsstand.anvil.AppScope
import dev.whosnickdoglio.newsstand.connectivity.ConnectionStatus
import dev.whosnickdoglio.newsstand.connectivity.ConnectivityModel
import dev.whosnickdoglio.newsstand.coroutines.CoroutineContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Default implementation of [ConnectivityModel].
 *
 * @see ConnectivityModel
 */
@ContributesBinding(AppScope::class)
class NewsstandConnectivityModel @Inject constructor(
    application: Application,
    private val coroutineContextProvider: CoroutineContextProvider
) : ConnectivityModel {

    private val connectedStateFlow = MutableStateFlow(ConnectionStatus.CONNECTED)

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            connectedStateFlow.update { ConnectionStatus.CONNECTED }
        }

        override fun onLost(network: Network) {
            connectedStateFlow.update { ConnectionStatus.DISCONNECTED }
        }

        override fun onUnavailable() {
            connectedStateFlow.update { ConnectionStatus.DISCONNECTED }
        }
    }

    init {
        // TODO do I want to unregister this?
        application.getSystemService(ConnectivityManager::class.java)
            .registerDefaultNetworkCallback(callback)
    }

    override suspend fun getCurrentConnectStatus(): ConnectionStatus =
        withContext(coroutineContextProvider.io) {
            connectedStateFlow.value
        }

    override val connectionStatus: Flow<ConnectionStatus> = connectedStateFlow
}
