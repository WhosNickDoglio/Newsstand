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

package dev.whosnickdoglio.feedly.networking.di

import com.squareup.anvil.annotations.ContributesTo
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dev.whosnickdoglio.auth.AuthenticationHeaderInterceptor
import dev.whosnickdoglio.auth.FeedlyAuthenticationService
import dev.whosnickdoglio.core.BuildInfo
import dev.whosnickdoglio.core.di.AppScope
import dev.whosnickdoglio.core.isDebug
import dev.whosnickdoglio.feedly.networking.FeedlyService
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@ContributesTo(AppScope::class)
object FeedlyNetworkModule {

    @Singleton
    @Provides
    fun provideOkhttpClient(
        authenticator: Authenticator,
        headerInterceptor: AuthenticationHeaderInterceptor,
        buildInfo: BuildInfo
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .apply {
                if (buildInfo.isDebug()) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .authenticator(authenticator) // TODO I don't think this works right
            .build()

    @Singleton
    @Provides
    fun provideAuthenticationService(): FeedlyAuthenticationService =
        FeedlyAuthenticationService.create(isProd = false)

    @Singleton
    @Provides
    fun provideFeedlyService(okHttpClient: Lazy<OkHttpClient>): FeedlyService =
        FeedlyService.create(okHttpClient, isProd = false)
}
