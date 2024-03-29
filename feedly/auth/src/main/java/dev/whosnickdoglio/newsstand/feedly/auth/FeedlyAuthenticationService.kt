/*
 * MIT License
 *
 * Copyright (c) 2021-2022 Nicholas Doglio
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

package dev.whosnickdoglio.newsstand.feedly.auth

import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.newsstand.feedly.models.ErrorResponse
import dev.whosnickdoglio.newsstand.feedly.models.Tokens
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@Suppress("LongParameterList")
interface FeedlyAuthenticationService {

    @GET("auth/auth")
    fun retrieveAuthCode(
        @Query("response_type") type: String = RESPONSE_TYPE,
        @Query("client_id") id: String,
        @Query("redirect_uri", encoded = true) redirect: String,
        @Query("scope", encoded = true) scope: String = SCOPE,
    ): Call<Unit>

    @FormUrlEncoded
    @POST("auth/token")
    suspend fun retrieveTokens(
        @Field("code") authCode: String,
        @Field("client_id") id: String,
        @Field("client_secret") secret: String,
        @Field("redirect_uri") redirect: String,
        @Field("grant_type") grant: String = "authorization_code",
        @Field("state") state: String? = null,
    ): ApiResult<Tokens, ErrorResponse>

    @FormUrlEncoded
    @POST("auth/token")
    suspend fun refreshTokens(
        @Field("refresh_token") token: String,
        @Field("client_id") id: String,
        @Field("client_secret") secret: String,
        @Field("grant_type") grant: String = "refresh_token",
    ): ApiResult<Tokens, ErrorResponse>

    @POST("auth/logout") suspend fun logOut()

    private companion object {
        private const val RESPONSE_TYPE = "code"
        private const val SCOPE = "https://cloud.feedly.com/subscriptions"
    }
}
