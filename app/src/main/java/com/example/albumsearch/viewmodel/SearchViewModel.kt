package com.example.albumsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albumsearch.model.network.ITunesService
import com.example.albumsearch.model.network.dto.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ViewModel for search screen
 */
class SearchViewModel : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    /**
     * Results of the last performed search
     */
    private val _searchResults = MutableLiveData<List<Album>>()
    val searchResults: LiveData<List<Album>>
        get() = _searchResults

    /**
     * [Album] to details of which to navigate
     */
    private val _navigateToDetails = MutableLiveData<Album?>()
    val navigateToDetails: LiveData<Album?>
        get() = _navigateToDetails

    /**
     * Performs an album search with the provided [term] and updates [searchResults] with the results
     *
     * @param term search keywords
     */
    fun performSearch(term: String) {
        coroutineScope.launch {
            try {
                _searchResults.value = ITunesService.searchAlbums(term).sortedBy { it.name }
            } catch (exception: Exception) {
                _searchResults.value = ArrayList()
            }
        }
    }

    /**
     * Notifies ViewModel about the [album] search result being clicked
     *
     * @param album
     */
    fun searchResultClicked(album: Album) {
        _navigateToDetails.value = album
    }

    /**
     * Notifies ViewModel about the navigation being completed
     */
    fun onDetailsNavigated() {
        _navigateToDetails.value = null
    }

    override fun onCleared() {
        super.onCleared()

        // Cancel pending coroutines
        job.cancel()
    }


}