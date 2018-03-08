package com.nicholasdoglio.data.local.model.feedly.authentication

data class AuthenticationRequest(
    val responseType: String,
    val clientId: String,
    val redirectUrl: String,
    val scope: String,
    val state: String = ""
)