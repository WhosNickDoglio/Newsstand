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

package dev.whosnickdoglio.newsstand.feedly.networking.di

import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import com.squareup.anvil.annotations.ContributesTo
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dev.whosnickdoglio.newsstand.anvil.AppScope
import dev.whosnickdoglio.newsstand.feedly.auth.FeedlyAuthenticationService
import dev.whosnickdoglio.newsstand.feedly.networking.internal.FeedlyService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@ContributesTo(AppScope::class)
object FeedlyNetworkModule {

    @Suppress("UnusedPrivateMember") // TODO fix this
    private const val PROD_BASE_URL = "https://cloud.feedly.com/v3/"
    private const val SANDBOX_BASE_URL = "https://sandbox7.feedly.com/v3/"

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: Lazy<OkHttpClient>): Retrofit = Retrofit.Builder()
        .baseUrl(SANDBOX_BASE_URL)
        .callFactory { request -> okHttpClient.get().newCall(request) }
        .addConverterFactory(ApiResultConverterFactory)
        .addCallAdapterFactory(ApiResultCallAdapterFactory)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideFeedlyService(retrofit: Retrofit): FeedlyService = retrofit.create()

    @Singleton
    @Provides
    fun provideFeedlyAuthService(retrofit: Retrofit): FeedlyAuthenticationService =
        retrofit.create()
}
