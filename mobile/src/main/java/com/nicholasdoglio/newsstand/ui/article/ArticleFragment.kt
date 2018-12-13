package com.nicholasdoglio.newsstand.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicholasdoglio.newsstand.R

class ArticleFragment : Fragment() {

  companion object {
    fun newInstance() = ArticleFragment()
  }

  private lateinit var viewModel: ArticleViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.article_fragment, container, false)
  }

}
