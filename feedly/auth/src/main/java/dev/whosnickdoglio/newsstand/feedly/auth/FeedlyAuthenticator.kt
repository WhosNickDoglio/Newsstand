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

import com.squareup.anvil.annotations.ContributesBinding
import dev.whosnickdoglio.newsstand.anvil.AppScope
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

@ContributesBinding(AppScope::class)
class FeedlyAuthenticator
@Inject
constructor(
    private val tokenStore: TokenRepository,
    private val authenticationHelper: AuthenticationHelper,
) : Authenticator {

    // TODO there's a better way to write this
    // TODO should I be checking expired time?

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.retryCount > MAX_RETRY_COUNT) {
            return null
        }

        // TODO don't use runBlocking
        runBlocking { authenticationHelper.refreshTokens() }

        val newToken = runBlocking { tokenStore.retrieveAccessToken() } ?: error(" No new token")

        return response.makeRequestWithToken(newToken)
    }

    private fun Response.makeRequestWithToken(token: String): Request {
        val tokenType = runBlocking { tokenStore.retrieveTokenType() }

        return request.newBuilder().header(AUTH_HEADER, "$tokenType $token").build()
    }

    private val Response.retryCount: Int
        get() {
            var currentResponse = priorResponse
            var result = 0
            while (currentResponse != null) {
                result++
                currentResponse = currentResponse.priorResponse
            }
            return result
        }

    private companion object {
        private const val MAX_RETRY_COUNT = 3
        private const val AUTH_HEADER = "Authorization"
    }
}
