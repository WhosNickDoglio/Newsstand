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

package dev.whosnickdoglio.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.anvil.annotations.ContributesBinding
import dev.whosnickdoglio.core.NewsstandDataStore
import dev.whosnickdoglio.core.di.AppScope
import dev.whosnickdoglio.onboarding.api.Feature
import dev.whosnickdoglio.onboarding.api.OnBoardingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class DefaultOnBoardingModel @Inject constructor(
    // TODO onboarding specific dataStore?
    @NewsstandDataStore private val appPreferencesDataStore: DataStore<Preferences>
) : OnBoardingModel {

    private val isOnBoardingCompleteKey = booleanPreferencesKey(IS_ON_BOARDING_COMPLETE_KEY)
    private val selectedFeatureKey = stringPreferencesKey(SELECTED_FEATURE)

    override val selectedFeature: Flow<Feature> = appPreferencesDataStore.data
        .map { value -> value[selectedFeatureKey]?.toFeature() ?: Feature.NO_SELECTION }

    override val isOnBoardingFinished: Flow<Boolean> = appPreferencesDataStore.data
        .map { value: Preferences -> value[isOnBoardingCompleteKey] ?: false }

    override suspend fun markOnBoardingComplete() {
        Timber.d("Onboarding has been marked complete.")
        appPreferencesDataStore.edit { pref -> pref[isOnBoardingCompleteKey] = true }
    }

    override suspend fun selectFeature(feature: Feature) {
        if (feature != Feature.NO_SELECTION) {
            Timber.d("Feature selected: $feature")
            appPreferencesDataStore.edit { pref -> pref[selectedFeatureKey] = feature.value }
        }
    }

    private fun String.toFeature(): Feature = Feature.values().first { it.value == this }

    private companion object {
        private const val IS_ON_BOARDING_COMPLETE_KEY = "IS_ON_BOARDING_COMPLETE"
        private const val SELECTED_FEATURE = "SELECTED_FEATURE"
    }
}
