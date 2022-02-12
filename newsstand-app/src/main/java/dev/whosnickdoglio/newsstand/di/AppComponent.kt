/*
 * MIT License
 *
 * Copyright (c) 2022 Nicholas Doglio
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

package dev.whosnickdoglio.newsstand.di

import android.app.Application
import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dev.whosnickdoglio.newsstand.anvil.AppScope
import javax.inject.Singleton

/**
 * A top level [dagger.Component] for our dependency graph, this [Component] should be a [Singleton]
 * and only initialized once per app launch via the [AppComponentProvider]. All
 * [dagger.Modules][dagger.Module] or classes that contribute to [AppScope] will be available in the
 * [AppComponent] graph.
 */
@Singleton
@MergeComponent(AppScope::class)
interface AppComponent {

    /**
     * A [Component.Factory] that outlines how we want to create this [AppComponent].
     */
    @Component.Factory
    interface Factory {

        /**
         * Creates an instance of a [AppComponent].
         *
         * @param application A [Application] object that will be bound to our Dagger graph via
         * [BindsInstance] for easy injection.
         */
        fun create(@BindsInstance application: Application): AppComponent
    }
}

/**
 * A class that provides and maintains a single instance of a [AppComponent].
 *
 * NOTE: This should **only** be applied to the Application class.
 */
interface AppComponentProvider {

    /**
     * An instance of the [AppComponent].
     */
    val component: AppComponent
}

/**
 * Exposes the [AppComponent] via an [android.content.Context] for easy member injection within
 * Android specific classes.
 */
val Context.injector get() = (applicationContext as AppComponentProvider).component
