/*
 * MIT License
 *
 * Copyright (c) 2021-2022 Nicholas Doglio
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

package dev.whosnickdoglio.newsstand.feedly.auth

import dev.whosnickdoglio.newsstand.coroutines.CoroutineContextProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationHeaderInterceptor @Inject constructor(
    private val tokenStore: TokenRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        runBlocking(coroutineContextProvider.io) {
            val token = tokenStore.retrieveAccessToken() ?: error("Token wasn't available")

            val tokenType = tokenStore.retrieveTokenType()

            val request = chain.request().newBuilder()
                .header(HEADER, " $tokenType $token")
                .build()

            chain.proceed(request)
        }

    private companion object {
        private const val HEADER = "Authorization"
    }
}
