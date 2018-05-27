package com.nicholasdoglio.data.local.model.feedly.entities

data class Feed(
    val id: String,
    val feedId: String,
    val subscribers: Int,
    val title: String,
    val description: String?,
    val language: String?,
    val velocity: Float?,
    val website: String?,
    val topics: List<String>?,
    val status: String?
)
