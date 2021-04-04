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

package com.ndoglio.feedly.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ndoglio.auth.TokenRepository
import com.ndoglio.core.AppScope
import com.ndoglio.core.DispatchersProvider
import com.ndoglio.feedly.models.Tokens
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class DataStoreTokenRepository @Inject constructor(
    @FeedlyDataStore private val store: DataStore<Preferences>,
    private val dispatchersProvider: DispatchersProvider
) : TokenRepository {

    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
    private val tokenTypeKey = stringPreferencesKey(TOKEN_TYPE_KEY)
    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)

    override val accessToken: Flow<String?> = store.data
        .flowOn(dispatchersProvider.background)
        .map { value: Preferences -> value[accessTokenKey] }

    override val tokenType: Flow<String?> = store.data
        .flowOn(dispatchersProvider.background)
        .map { value: Preferences -> value[tokenTypeKey] }

    override val refreshToken: Flow<String?> = store.data
        .flowOn(dispatchersProvider.background)
        .map { value: Preferences -> value[refreshTokenKey] }

    override suspend fun setTokens(container: Tokens) {
        Timber.d("New Tokens set: $container")
        withContext(dispatchersProvider.background) {
            store.edit { pref ->
                pref[accessTokenKey] = container.access
                pref[tokenTypeKey] = container.tokenType
                if (container.refresh != null) {
                    pref[refreshTokenKey] = container.refresh!!
                }
            }
        }
    }

    override suspend fun clear() {
        withContext(dispatchersProvider.background) {
            store.edit { it.clear() }
        }
    }

    private companion object {
        private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN"
        private const val TOKEN_TYPE_KEY = "TOKEN_TYPE"
        private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}
