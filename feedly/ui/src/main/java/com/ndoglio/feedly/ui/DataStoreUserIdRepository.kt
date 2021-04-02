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
import com.ndoglio.auth.UserIdRepository
import com.ndoglio.core.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class DataStoreUserIdRepository @Inject constructor(
    @FeedlyDataStore private val store: DataStore<Preferences>,
) : UserIdRepository {
    private val userIdKey = stringPreferencesKey(USER_ID_KEY)

    override val currentUserId: String?
        get() = runBlocking {
            store.data
                .map { value -> value[userIdKey] }
                .first()
        }
    override val user: Flow<String?>
        get() =   store.data.map { value -> value[userIdKey] }

    override fun setUserId(id: String) {
        Timber.i("New User ID: $id")
        runBlocking { store.edit { pref -> pref[userIdKey] = id } }
    }

    override fun clear() {
        // TODO is this the best way to do this?
        runBlocking {
            store.edit {
                it.clear()
            }
        }
    }

    private companion object {
        private const val USER_ID_KEY = "USER_ID"
    }
}
