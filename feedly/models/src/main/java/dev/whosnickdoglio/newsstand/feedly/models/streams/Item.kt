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

package dev.whosnickdoglio.newsstand.feedly.models.streams

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    val id: String, // Fc8eWZQhAo/nbGP3V9wtomJqhIKLh7hWWbPoxOUItFg=_17711a5aeca:2da:bc7cc76b
    val keywords: List<String>? = null,
    val originId: String, // 5e1c9678b75a570f5fd57d69:5e9f3b7b4644dd371e0252bc:6002f45612a04d6cc4884a9b
    val fingerprint: String, // 6932e2
    val content: Content? = null,
    val title: String, // Game Day: Knicks @ Celtics, 1/17/21
    val author: String? = null, // Jonathan Schulman
    val crawled: Long, // 1610908806858
    val origin: Origin? = null,
    val published: Long, // 1610895141000
    val summary: Summary? = null,
    val alternate: List<Alternate>,
    val visual: Visual? = null,
    val unread: Boolean, // true
    val categories: List<Category>,
    val webfeeds: Webfeeds? = null,
    val updated: Long? = null, // 1610979541764
)
