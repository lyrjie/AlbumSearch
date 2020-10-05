package com.example.albumsearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.albumsearch.databinding.FragmentSearchBinding
import com.example.albumsearch.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)

        setupSearchCallback(binding.inputSearchTerm)

        return binding.root
    }

    /**
     * Wires up ViewModel to [searchView]
     *
     * @param searchView [SearchView] to connect to
     */
    private fun setupSearchCallback(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.performSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // We don't need to do anything here since we don't have search suggestions
                return false
            }
        })
    }
}