package com.nicholasdoglio.data.local.model.feedly.authentication

import com.nicholasdoglio.data.util.Seconds

data class AuthenticationRefreshReponse(
    val userId: String,
    val accessToken: String,
    val expiresIn: Seconds,
    val tokenType: String = "Bearer",
    val plan: String
)


