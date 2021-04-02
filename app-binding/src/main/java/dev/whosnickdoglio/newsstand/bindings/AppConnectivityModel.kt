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

package dev.whosnickdoglio.newsstand.bindings

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.squareup.anvil.annotations.ContributesBinding
import com.squareup.workflow1.Worker
import dev.whosnickdoglio.core.ConnectivityModel
import dev.whosnickdoglio.core.coroutines.CoroutineContextProvider
import dev.whosnickdoglio.core.di.AppScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class AppConnectivityModel @Inject constructor(
    application: Application,
    private val coroutineContextProvider: CoroutineContextProvider
) : ConnectivityModel {

    private val connectedStateFlow = MutableStateFlow(true)

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            connectedStateFlow.value = true
        }

        override fun onLost(network: Network) {
            connectedStateFlow.value = false
        }

        override fun onUnavailable() {
            connectedStateFlow.value = false
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
        }
    }

    init {
        // TODO do I want to unregister this?
        application.getSystemService(ConnectivityManager::class.java)
            .registerDefaultNetworkCallback(callback)
    }

    override suspend fun isConnected(): Boolean = withContext(coroutineContextProvider.io) {
        connectedStateFlow.value
    }

    override val isConnected: Flow<Boolean> = connectedStateFlow
}

// TODO do I observe this at the app-level to show a modal whenever connection is lost?
//  Or use it on a workflow by workflow basis for screens that require connection?
class ConnectivityWorker @Inject constructor(
    private val model: ConnectivityModel
) : Worker<Boolean> {
    override fun run(): Flow<Boolean> = model.isConnected
}
