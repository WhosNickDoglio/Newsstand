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

package dev.whosnickdoglio.newsstand

import com.squareup.anvil.annotations.ContributesBinding
import dev.whosnickdoglio.core.di.AppScope
import dev.whosnickdoglio.newsstand.di.AppComponent
import dev.whosnickdoglio.onboarding.api.Feature
import javax.inject.Inject
import javax.inject.Singleton

interface FeatureManager {

    fun createComponent(feature: Feature)

    fun dropComponent(feature: Feature)

    fun getComponentByFeature(feature: Feature): FeatureComponent // TODO typing?
}

// TODO do i need this?
@Singleton
@ContributesBinding(AppScope::class)
class DefaultFeatureManager @Inject constructor(private val component: AppComponent) :
    FeatureManager {

    private val components: MutableMap<Feature, FeatureComponent> = mutableMapOf()

    override fun createComponent(feature: Feature) {
        val newComponent = when (feature) {
            Feature.FEEDLY -> component.feedlyFactory.create()
            else -> error("This feature is not supported yet: $feature")
        }

        components[feature] = newComponent
    }

    override fun dropComponent(feature: Feature) {
        components.remove(feature)
    }

    override fun getComponentByFeature(feature: Feature): FeatureComponent {
        return components[feature] ?: error("Feature component has not been created yet.")
    }

    fun <T : FeatureComponent> getComponent(feature: Feature): T {
        return (components[feature] ?: error("FUCK")) as T
    }
}

sealed interface FeatureComponent

interface Feedly : FeatureComponent

interface InoReader : FeatureComponent

interface Rss : FeatureComponent
