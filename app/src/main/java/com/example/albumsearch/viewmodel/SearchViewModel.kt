package com.example.albumsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.albumsearch.R
import com.example.albumsearch.model.network.ApiStatus
import com.example.albumsearch.model.AlbumRepository
import com.example.albumsearch.model.network.dto.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ViewModel for search screen
 */
class SearchViewModel : BaseViewModel() {

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
     * True if the search input should lose focus
     */
    private val _clearSearchFocus = MutableLiveData<Boolean>()
    val clearSearchFocus: LiveData<Boolean>
        get() = _clearSearchFocus

    /**
     * Status of the last API call
     */
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    /**
     * Performs an album search with the provided [term] and updates [searchResults] with the results
     *
     * @param term search keywords
     */
    fun performSearch(term: String) {
        _clearSearchFocus.value = true
        coroutineScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                val albums = AlbumRepository.searchAlbums(term).sortedBy { it.name }
                _searchResults.value = albums
                if (albums.isEmpty()) _toastMessage.value = R.string.nothing_was_found
                _status.value = ApiStatus.DONE
            } catch (exception: Exception) {
                _status.value = ApiStatus.ERROR
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

    /**
     * Notifies ViewModel about search losing focus
     */
    fun onSearchFocusCleared() {
        _clearSearchFocus.value = null
    }

    override fun onCleared() {
        super.onCleared()

        // Cancel pending coroutines
        job.cancel()
    }


}