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

package dev.whosnickdoglio.feedly.networking

import dev.whosnickdoglio.auth.UserIdRepository
import javax.inject.Inject

// TODO this feels bad, how else can I do this?
class GlobalResourceIdProvider @Inject constructor(
    private val userIdRepository: UserIdRepository
) {

//    val allArticlesId: String
//        get() = "user/${userIdRepository.user}/category/global.all"
//
//    val allArticlesUncategorizedId: String
//        get() = "user/${userIdRepository.user}/category/global.uncategorized"
//
//    val allMustReadArticlesId: String // TODO is this sources?
//        get() = "user/${userIdRepository.user}/category/global.must"
//
//    val allEntriesRecentlyReadId: String
//        get() = "user/${userIdRepository.user}/tag/global.read"
//
//    val allSavedEntriesId: String
//        get() = "user/${userIdRepository.user}/tag/global.saved"
//
//    val allArticlesFromAllTagsId: String
//        get() = "user/${userIdRepository.user}/tag/global.all"
//
//    val allAnnotatedArticlesId: String
//        get() = "user/${userIdRepository.user}/tag/global.annotated"
//
//    val allArticlesFromPriorityFilterId: String
//        get() = "user/${userIdRepository.user}/priority/global.al"

    // TODO enterprise global
}
