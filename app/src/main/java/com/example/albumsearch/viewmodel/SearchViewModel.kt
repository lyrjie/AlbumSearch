package com.example.albumsearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.albumsearch.model.AlbumRepository
import com.example.albumsearch.model.database.entities.AlbumEntity
import com.example.albumsearch.model.network.ApiStatus
import com.example.albumsearch.model.network.dto.AlbumDto
import kotlinx.coroutines.*

/**
 * ViewModel for search screen
 */

class SearchViewModel
@ViewModelInject
constructor(private val repository: AlbumRepository) : BaseViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    /**
     * Keyphrase to search by
     */
    private val searchTerm = MutableLiveData<String>()

    /**
     * Results of the last performed search
     */
    val searchResults = Transformations.switchMap(searchTerm) {
        repository.getFilteredAlbums(it)
    }

    /**
     * [AlbumDto] to details of which to navigate
     */
    private val _navigateToDetails = MutableLiveData<AlbumEntity?>()
    val navigateToDetails: LiveData<AlbumEntity?>
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
                searchTerm.value = term
                repository.performSearch(term)
                _status.value = ApiStatus.DONE
            } catch (exception: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    /**
     * Notifies ViewModel about the [album] search result being clicked
     *
     * @param album
     */
    fun searchResultClicked(album: AlbumEntity) {
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