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

package dev.whosnickdoglio.feedly.models

import com.squareup.moshi.JsonClass

/**
 *
 *
 * @param id
 * @param feedId
 * @param title
 * @param topics
 * @param state
 * @param velocity
 * @param subscribers
 * @param updated
 * @param website
 * @param iconUrl
 * @param partial
 * @param estimatedEngagement
 * @param coverUrl
 * @param visualUrl
 * @param language
 * @param description
 * @param coverColor
 * @param logo
 * @param wordmark
 * @param accentColor
 *
 * @see <a href="https://developer.feedly.com/v3/feeds/">Feedly docs</a>
 */
@JsonClass(generateAdapter = true)
data class Feed(
    val id: String,
    val feedId: String,
    val title: String,
    val topics: List<String>? = null,
    val state: String? = null,
    val velocity: Float? = null,
    val subscribers: Int,
    val updated: Long?, // TODO what are you????
    val website: String,
    val iconUrl: String? = null,
    val partial: Boolean? = null, // TODO what are you????
    val estimatedEngagement: String? = null,
    val coverUrl: String? = null,
    val visualUrl: String? = null,
    val language: String? = null,
    val description: String? = null,
    val coverColor: Int? = null, // TODO this might get a custom adapter?
    val logo: String? = null,
    val wordmark: String? = null,
    val accentColor: String? = null,
)
