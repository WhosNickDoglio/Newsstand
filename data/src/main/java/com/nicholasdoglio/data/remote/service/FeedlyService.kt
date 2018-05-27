package com.nicholasdoglio.data.remote.service

import com.nicholasdoglio.data.local.model.feedly.authentication.AuthenticationActivationRequest
import com.nicholasdoglio.data.local.model.feedly.authentication.AuthenticationActivationResponse
import com.nicholasdoglio.data.local.model.feedly.authentication.AuthenticationRefreshReponse
import com.nicholasdoglio.data.local.model.feedly.authentication.AuthenticationRefreshRequest
import com.nicholasdoglio.data.local.model.feedly.entities.Category
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

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
}