package com.nicholasdoglio.newsstand.ui.about.libs

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LibsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
