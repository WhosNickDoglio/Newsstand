package com.nicholasdoglio.newsstand.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicholasdoglio.newsstand.R

class AboutFragment : Fragment() {

  companion object {
    fun newInstance() = AboutFragment()
  }

  private lateinit var viewModel: AboutViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.about_fragment, container, false)
  }

}
