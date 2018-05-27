package com.nicholasdoglio.data.local.model.feedly.entities

import com.nicholasdoglio.data.util.Timestamp

data class Entry(
    val entryId: String,
    val title: String?,
    val content: String?,
    val summary: String?,
    val author: String?,
    val crawl: Timestamp,
    val recrawled: Timestamp?,
    val published: Timestamp,
    val updated: Timestamp?,
    val alternate: List<Link>?,
    val origin: Origin?,
    val keywords: List<String>?,
    val visual: Visual?,
    val unread: Boolean,
    val tags: List<Tag>?,
    val categories: List<Category>,
    val engagment: Int,
    val actionTimestamp: Timestamp?,
    val enclosure: List<Link>?,
    val fingerprint: String,
    val originId: String,
    val sid: String?
)