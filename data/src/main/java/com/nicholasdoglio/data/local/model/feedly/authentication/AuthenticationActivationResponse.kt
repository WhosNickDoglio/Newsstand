package com.nicholasdoglio.data.local.model.feedly.authentication

import com.nicholasdoglio.data.util.Seconds

data class AuthenticationActivationResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Seconds,
    val plan: String
)