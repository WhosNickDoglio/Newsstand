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

package dev.whosnickdoglio.feedly.ui.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.squareup.workflow1.ui.compose.composeViewFactory

@Composable
internal fun <T> FeedContent(
    data: List<T>,
    onItemClicked: (T) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onSearch: () -> Unit,
    onScroll: () -> Unit,
    isLoading: Boolean
) {
    val refreshState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        state = refreshState,
        onRefresh = onRefresh
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {

            }
        }
    }
}

@Preview
@Composable
private fun PreviewFeedContent() {
    FeedContent(
        data = emptyList<String>(),
        onItemClicked = {},
        onRefresh = {},
        isRefreshing = false,
        onSearch = {},
        onScroll = {},
        isLoading = false
    )

}

internal val feedViewFactory = composeViewFactory<FeedWorkflow.Rendering> { rendering, _ ->
    FeedContent(
        data = rendering.data,
        onItemClicked = { rendering.onItemClicked() },
        onRefresh = rendering.onRefresh,
        isRefreshing = rendering.showRefresher,
        onSearch = rendering.onSearchFabPressed,
        onScroll = rendering.onScroll,
        isLoading = rendering.showLoader
    )
}
