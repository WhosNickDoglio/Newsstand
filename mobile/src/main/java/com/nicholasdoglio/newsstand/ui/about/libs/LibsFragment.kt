package com.nicholasdoglio.newsstand.ui.about.libs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nicholasdoglio.newsstand.R

class LibsFragment : Fragment() {

  companion object {
    fun newInstance() = LibsFragment()
  }

  private lateinit var viewModel: LibsViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.libs_fragment, container, false)
  }


}
