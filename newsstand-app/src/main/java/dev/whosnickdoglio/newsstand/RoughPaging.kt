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

package dev.whosnickdoglio.newsstand

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable // First attempt
fun <T> PagingColumn(
        data: SnapshotStateList<T>,
        fetchIndex: Int,
        fetchMoreContent: () -> Unit,
        content: @Composable (T) -> Unit
) {
    LazyColumn {
        itemsIndexed(data) { index, item ->
            // TODO this logic should be pulled out?
            //  How best to notify the Workflow of when to fetch more content?
            if ((data.size - fetchIndex) == index) {
                // should this be in a LaunchEffect?
                fetchMoreContent()
            }

            content(item)
        }
    }
}

@Composable
private fun PagingColumnUsage() {
    val data = MutableList(50) { "DUMMY DATA" }.toMutableStateList()

    PagingColumn(
            data = data,
            fetchIndex = 5,
            fetchMoreContent = {
                val moreData = List(10) { "DUMMY DATA" }
                data.addAll(moreData)

            }
    ) {
        Text(it)
    }
}

@Composable
fun <T> Scratch(
        data: SnapshotStateList<T>,
        fetchIndex: Int,
        fetchMoreContent: () -> Unit,
        shouldLoadMore: (Boolean) -> Unit,
        content: @Composable (T) -> Unit
) {
    val listState = rememberLazyListState()

    val loadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItemsNumber - fetchIndex)
        }
    }

    shouldLoadMore(loadMore)

    LazyColumn(state = listState) {
        itemsIndexed(data) { index, item ->
            // TODO this logic should be pulled out?
            //  How best to notify the Workflow of when to fetch more content?
            if ((data.size - fetchIndex) == index) {
                // should this be in a LaunchEffect?
                fetchMoreContent()
            }

            content(item)
        }
    }
}


// Pulled from Kotlin Slack
@Composable
fun Pagination(
        listState: LazyListState,
        buffer: Int = 5,
        action: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }
    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
                .distinctUntilChanged()
                .collect {
                    if (it) {
                        action()
                    }
                }
    }
}
