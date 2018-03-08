package com.nicholasdoglio.data.remote

import com.nicholasdoglio.data.local.model.feedly.authentication.AuthenticationRequest
import com.nicholasdoglio.data.local.model.feedly.authentication.AuthenticationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FeedlyService {

    @GET("auth/auth/")
    fun authenticateUser(@Body authenticationRequest: AuthenticationRequest): Call<Response<AuthenticationResponse>>

    @POST("auth/token/")
    fun returnAuth()

    @POST("auth/token/")
    fun refreshAuth()


    companion object {
        val FeedlyClient = Retrofit.Builder()
            .baseUrl("https://sandbox7.feedly.com/v3/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(FeedlyService::class.java)
    }
}