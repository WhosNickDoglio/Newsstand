/*
 * MIT License
 *
 * Copyright (c) 2019 Nicholas Doglio
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

package com.nicholasdoglio.data.remote.service

/*
interface FeedlyService {

    @POST("auth/token/")
    fun authenticateUserActivation(@Body authenticationActivationRequest: AuthenticationActivationRequest): Call<Response<AuthenticationActivationResponse>>

    @POST("auth/token/")
    fun authenticateUserRefresh(@Body authenticationRefreshRequest: AuthenticationRefreshRequest): Call<Response<AuthenticationRefreshReponse>>

    @GET("categories/")
    fun getCategories(@Header("Authorization") authorizationToken: String): Call<Response<List<Category>>>

    @POST("categories/{categoriesId}")
    fun updateCategoryLabel(@Header("Authorization") authorizationToken: String, @Path("categoriesId") currentCategoryId: String, @Body newLabel: String): Call<Response<Unit>>

    @DELETE("categories/{categoriesId}")
    fun deleteCategory(@Header("Authorization") authorizationToken: String, @Path("categoriesId") currentCategoryId: String): Call<Response<Unit>>

    companion object {
        val feedlyClient = Retrofit.Builder()
            .baseUrl("https://sandbox7.feedly.com/v3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(FeedlyService::class.java)
    }
}*/
