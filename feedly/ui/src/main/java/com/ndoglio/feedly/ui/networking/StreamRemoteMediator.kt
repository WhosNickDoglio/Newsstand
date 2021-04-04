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

package com.ndoglio.feedly.ui.networking

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ndoglio.feedly.FeedlyClient
import com.ndoglio.feedly.models.streams.Stream
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@OptIn(ExperimentalPagingApi::class)
class StreamRemoteMediator @AssistedInject constructor(
    private val client: FeedlyClient,
    @Assisted private val streamId: String,
) : RemoteMediator<StreamParams, Stream>() {

    @AssistedFactory
    interface Factory {
        fun create(streamId: String): StreamRemoteMediator
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<StreamParams, Stream>
    ): MediatorResult {

        val lastPage = state.closestPageToPosition(state.anchorPosition ?: 0)?.nextKey

        val stream = client.getStream(
            id = streamId,
            count = lastPage?.count,
            ranked = lastPage?.ranked,
            unreadOnly = lastPage?.unreadOnly,
            newerThan = lastPage?.newerThan,
            continuation = if (loadType == LoadType.REFRESH) null else lastPage?.continuation,
            showMuted = lastPage?.showMuted

        )

        return TODO()
    }
}

data class StreamParams(
    val id: String,
    val count: Int? = null,
    val ranked: String? = null,
    val unreadOnly: Boolean? = null,
    val newerThan: Long? = null,
    val continuation: String? = null,
    val showMuted: Boolean? = null,
)
