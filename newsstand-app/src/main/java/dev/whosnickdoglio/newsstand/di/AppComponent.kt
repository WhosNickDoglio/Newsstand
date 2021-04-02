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

package dev.whosnickdoglio.newsstand.di

import android.app.Application
import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dev.whosnickdoglio.core.di.AppScope
import dev.whosnickdoglio.newsstand.MainActivity
import javax.inject.Singleton

/**
 * Top-level Dagger [dagger.Component] to manage app level dependencies.
 *
 * TODO mention anvil
 */
@Singleton
@MergeComponent(AppScope::class)
interface AppComponent {

    /**
     *
     */
    @Component.Factory
    interface Factory {
        /**
         * Creates an instance of [AppComponent].
         *
         * @param application The provided [Application] that will be bound to the Dagger
         * graph using [BindsInstance].
         */
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

    val feedlyFactory: FeedlyComponent.Factory

    val activityComponentFactory: ActivityComponent.Factory
}

interface AppComponentProvider {
    val component: AppComponent
}

val Context.injector get() = (applicationContext as AppComponentProvider).component
