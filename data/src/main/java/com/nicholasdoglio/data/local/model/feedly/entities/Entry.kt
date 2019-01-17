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