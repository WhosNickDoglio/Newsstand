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

package dev.whosnickdoglio.feedly.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.whosnickdoglio.auth.TokenRepository
import dev.whosnickdoglio.core.di.AppScope
import dev.whosnickdoglio.feedly.models.Tokens
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class DataStoreTokenRepository @Inject constructor(
    @FeedlyDataStore private val store: DataStore<Preferences>,
) : TokenRepository {

    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
    private val tokenTypeKey = stringPreferencesKey(TOKEN_TYPE_KEY)
    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)

    override suspend fun retrieveAccessToken(): String? = store.data
        .map { value: Preferences -> value[accessTokenKey] }.first()

    override suspend fun retrieveRefreshToken(): String? = store.data
        .map { value: Preferences -> value[refreshTokenKey] }.first()

    override suspend fun retrieveTokenType(): String? = store.data
        .map { value: Preferences -> value[tokenTypeKey] }.first()

    override suspend fun setTokens(container: Tokens) {
        Timber.d("New Tokens set: $container")
        store.edit { pref ->
            pref[accessTokenKey] = container.access
            pref[tokenTypeKey] = container.tokenType
            if (container.refresh != null) {
                pref[refreshTokenKey] = container.refresh!!
            } else {
                // todo clear refresh token?
            }
        }
    }

    override suspend fun clear() {
        store.edit { pref ->
            pref.remove(accessTokenKey)
            pref.remove(tokenTypeKey)
            pref.remove(refreshTokenKey)
        }
    }

    private companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN"
        private const val TOKEN_TYPE_KEY = "TOKEN_TYPE"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}
