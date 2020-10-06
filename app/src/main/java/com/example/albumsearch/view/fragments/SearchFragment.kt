package com.example.albumsearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsearch.R
import com.example.albumsearch.databinding.FragmentSearchBinding
import com.example.albumsearch.view.adapters.AlbumAdapter
import com.example.albumsearch.view.adapters.AlbumOnClickListener
import com.example.albumsearch.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    /**
     * Search results adapter
     */
    private lateinit var adapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupResultsRecycler(binding.searchResultsRecycler)
        setupSearchCallback(binding.inputSearchTerm)
        setupSearchFocusClear(binding.inputSearchTerm)
        setupNavigation()

        return binding.root
    }

    private fun setupNavigation() {
        viewModel.navigateToDetails.observe(viewLifecycleOwner, {
            it?.let {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.fragment_open_enter,
                        R.anim.fragment_fade_exit,
                        R.anim.fragment_fade_enter,
                        R.anim.fragment_close_exit
                    )
                    .addToBackStack(null)
                    .replace(R.id.main_content, AlbumDetailFragment.newInstance(it))
                    .commit()
                viewModel.onDetailsNavigated()
            }
        })
    }

    /**
     * Initializes [search_results_recycler]
     */
    private fun setupResultsRecycler(searchResultsRecycler: RecyclerView) {
        // Setup RecyclerView.Adapter
        adapter = AlbumAdapter(AlbumOnClickListener {
            viewModel.searchResultClicked(it)
        })
        searchResultsRecycler.adapter = adapter

        // Listen for result updates
        viewModel.searchResults.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            // Hide RecyclerView if there's nothing to display
            searchResultsRecycler.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
        })
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

    /**
     * Sets up [searchView] focus clearing
     *
     * @param searchView
     */
    private fun setupSearchFocusClear(searchView: SearchView) {
        viewModel.clearSearchFocus.observe(viewLifecycleOwner, {
            if (it == true) {
                searchView.clearFocus()
                viewModel.onSearchFocusCleared()
            }
        })
    }
}