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

package com.ndoglio.auth

import com.ndoglio.core.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface AuthenticationHelper {

    fun provideRedirectUrl(): String

    suspend fun isLoggedIn(): Boolean

    suspend fun retrieveTokens(url: String)

    suspend fun refreshTokens()

    suspend fun logOut()
}

@ContributesBinding(AppScope::class)
class DefaultAuthenticationHelper @Inject constructor(
    private val authenticationService: FeedlyAuthenticationService,
    private val tokenRepository: TokenRepository,
    private val userIdRepository: UserIdRepository
) : AuthenticationHelper {

    override suspend fun isLoggedIn(): Boolean = userIdRepository.user.first() != null

    override fun provideRedirectUrl(): String = authenticationService.retrieveAuthCode(
        id = CLIENT_ID,
        redirect = REDIRECT_URI,
    ).request().url.toString()

    override suspend fun retrieveTokens(url: String) {
        val code = url.substringAfter(CODE)

        val tokens = authenticationService.retrieveTokens(
            authCode = code,
            id = CLIENT_ID,
            secret = CLIENT_SECRET,
            redirect = REDIRECT_URI
        )

        userIdRepository.setUserId(tokens.id)
        tokenRepository.setTokens(tokens)
    }

    override suspend fun refreshTokens() {
        val newTokens = authenticationService.refreshTokens(
            token = tokenRepository.refreshToken.first() ?: error("No token!"),
            id = CLIENT_ID,
            secret = CLIENT_SECRET
        )

        userIdRepository.setUserId(newTokens.id)
        tokenRepository.setTokens(newTokens)
    }

    override suspend fun logOut() {
        authenticationService.logOut()
        userIdRepository.clear()
        tokenRepository.clear()
    }

    private companion object {
        private const val CODE = "?code="
        private const val CLIENT_ID = "sandbox"
        private const val CLIENT_SECRET = "X92Fj8xZnTWO0BUMlzrEHY00Lc_JN_DK"
        private const val REDIRECT_URI = "https://localhost"
    }
}
