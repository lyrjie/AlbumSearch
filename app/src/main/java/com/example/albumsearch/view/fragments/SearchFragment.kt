package com.example.albumsearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.albumsearch.R
import com.example.albumsearch.databinding.FragmentSearchBinding
import com.example.albumsearch.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }
}