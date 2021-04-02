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

package com.ndoglio.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.ndoglio.core.AppScope
import com.ndoglio.core.NewsstandDataStore
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface OnboardingModel {

    val onboardingIsComplete: Flow<Boolean>

    val selectedFeature: Flow<Feature?>

    suspend fun markOnboardingComplete()

    suspend fun selectFeature(feature: Feature)
}

@ContributesBinding(AppScope::class)
class DefaultOnboardingModel @Inject constructor(
    @NewsstandDataStore private val store: DataStore<Preferences>
) : OnboardingModel {

    private val onboardingKey = booleanPreferencesKey(IS_ONBOARDING_COMPLETE_KEY)

    override val onboardingIsComplete: Flow<Boolean> =
        store.data.map { value: Preferences -> value[onboardingKey] ?: false }.filter { it }

    override val selectedFeature: Flow<Feature?>
        get() = TODO("Not yet implemented")

    override suspend fun markOnboardingComplete() {
        store.edit { pref -> pref[onboardingKey] = true }
    }

    override suspend fun selectFeature(feature: Feature) {
        TODO("Not yet implemented")
    }

    private companion object {
        private const val IS_ONBOARDING_COMPLETE_KEY = "IS_ONBOARDING_COMPLETE"
        private const val FEATURE_SELECTED_KEY = "FEATURE_SELECTED"
    }
}