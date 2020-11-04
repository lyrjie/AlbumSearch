package com.example.albumsearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.albumsearch.model.AlbumRepository
import com.example.albumsearch.model.database.entities.AlbumEntity
import com.example.albumsearch.model.network.ApiStatus
import com.example.albumsearch.model.network.dto.AlbumDto
import com.example.albumsearch.util.SingleLiveEvent
import kotlinx.coroutines.launch

/** ViewModel for search screen */

class SearchViewModel
@ViewModelInject
constructor(private val repository: AlbumRepository) : BaseViewModel() {

    /** Keyphrase to search by */
    private val searchTerm = MutableLiveData<String>()

    /** Results of the last performed search */
    val searchResults = Transformations.switchMap(searchTerm) {
        repository.getFilteredAlbums(it)
    }

    /** [AlbumDto] to details of which to navigate */
    val navigateToDetails = SingleLiveEvent<AlbumEntity>()

    /** True if the search input should lose focus */
    val clearSearchFocus = SingleLiveEvent<Boolean>()

    /** Status of the last API call */
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    /**
     * Performs an album search with the provided [term] and updates [searchResults] with the results
     *
     * @param term search keywords
     */
    fun performSearch(term: String) {
        clearSearchFocus.value = true
        viewModelScope.launch {
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

    /** Notifies ViewModel about the [album] search result being clicked */
    fun searchResultClicked(album: AlbumEntity) {
        navigateToDetails.value = album
    }

}