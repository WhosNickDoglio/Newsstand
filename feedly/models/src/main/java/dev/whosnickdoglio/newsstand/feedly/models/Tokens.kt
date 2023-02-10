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

package dev.whosnickdoglio.newsstand.feedly.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * TODO add more here
 *
 * @param id The logged in user's feedly id.
 * @param refresh A token that may be used to obtain a new access token. Refresh tokens are valid
 *   until the user revokes access.
 * @param access A token that may be used to access APIs. This token expires in the given amount of
 *   time in [expiresIn], it can also be refreshed using [refresh].
 * @param expiresIn The remaining lifetime on the access token in **seconds**.
 * @param tokenType Indicates the type of token returned. At this time, this field will always have
 *   the value of Bearer
 * @param plan Indicated the user plan (standard, pro or business)
 * @param state The state that was passed in
 * @see <a href src="https://developer.feedly.com/v3/auth/">Feedly auth docs</a>
 */
@JsonClass(generateAdapter = true)
data class Tokens(
    val id: String,
    @Json(name = "refresh_token") val refresh: String? = null,
    @Json(name = "access_token") val access: String,
    @Json(name = "expires_in") val expiresIn: Int,
    @Json(name = "token_type") val tokenType: String,
    val plan: String,
    val state: String? = null
)
