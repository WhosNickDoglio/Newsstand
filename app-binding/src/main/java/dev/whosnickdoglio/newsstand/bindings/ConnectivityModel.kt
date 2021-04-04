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
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.ndoglio.core.ConnectivityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class AndroidTenAndAboveConnectivityModel @Inject constructor(application: Application) :
    ConnectivityModel {

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val mutableIsConnected: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    init {
        // connectivityManager.registerNetworkCallback(
        //     NetworkRequest.Builder().build(),
        //     object : ConnectivityManager.NetworkCallback() {
        //         override fun onAvailable(network: Network) {
        //             super.onAvailable(network)
        //             mutableIsConnected.value = true
        //         }
        //
        //         override fun onLost(network: Network) {
        //             super.onLost(network)
        //             mutableIsConnected.value = false
        //         }
        //
        //         override fun onUnavailable() {
        //             super.onUnavailable()
        //             mutableIsConnected.value = false
        //         }
        //     }
        // )
    }

    override val isConnected: Flow<Boolean>
        get() = mutableIsConnected.filterNotNull().distinctUntilChanged()
}

class AndroidNineAndBelowConnectivityModel @Inject constructor() : ConnectivityModel {
    override val isConnected: Flow<Boolean>
        get() = TODO("Not yet implemented")
}
