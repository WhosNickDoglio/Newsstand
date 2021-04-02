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

package com.ndoglio.feedly.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ndoglio.core.Screen
import com.ndoglio.feedly.ui.databinding.FeedItemViewholderBinding
import com.ndoglio.feedly.ui.databinding.FeedViewBinding
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.ui.LayoutRunner
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory
import javax.inject.Inject

class FeedlyWorkflow @Inject constructor() :
// TODO this will eventually manage the whole backstack for feedly, just playing around right now.
// StatefulWorkflow<FeedlyProps, FeedlyState, Nothing, BackStackScreen<Screen>>() {
    StatefulWorkflow<FeedlyProps, FeedlyState, Nothing, HomeScreen>() {

    override fun initialState(props: FeedlyProps, snapshot: Snapshot?): FeedlyState =
        FeedlyState.Home

    override fun render(
        renderProps: FeedlyProps,
        renderState: FeedlyState,
        context: RenderContext
    ): HomeScreen = HomeScreen(
        items = List(200) { "$it Hello World" },
        onSearchFabPressed = {},
        onPullToRefresh = {}
    )

    override fun snapshotState(state: FeedlyState): Snapshot? = null
}

data class FeedlyProps(
    val codeUrl: String? = null,
    val context: Context? = null
)

sealed class FeedlyState {
    object Login : FeedlyState()
    object Home : FeedlyState()
    object ManageFeeds : FeedlyState()
    object HomeMenu : FeedlyState()
    object Settings : FeedlyState()
    object Search : FeedlyState()
}

data class HomeScreen(
    val items: List<String>,
    val onSearchFabPressed: () -> Unit,
    val onPullToRefresh: () -> Unit,

    ) : Screen

class HomeLayoutRunner(
    private val binding: FeedViewBinding
) : LayoutRunner<HomeScreen> {
    override fun showRendering(rendering: HomeScreen, viewEnvironment: ViewEnvironment) {
        with(binding) {
            swipeToRefresh.setOnRefreshListener { rendering.onPullToRefresh() }
            searchFab.setOnClickListener { rendering.onSearchFabPressed() }

            recyclerView.apply {
                adapter = HomeAdapter(rendering.items)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    companion object : ViewFactory<HomeScreen> by LayoutRunner.bind(
        FeedViewBinding::inflate, ::HomeLayoutRunner
    )
}

private class HomeAdapter(private val data: List<String>) :
    RecyclerView.Adapter<SimpleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder =
        SimpleViewHolder(FeedItemViewholderBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

private class SimpleViewHolder(private val binding: FeedItemViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.text.text = item
    }
}