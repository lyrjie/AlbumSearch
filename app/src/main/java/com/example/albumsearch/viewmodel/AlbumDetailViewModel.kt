package com.example.albumsearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.albumsearch.R
import com.example.albumsearch.model.AlbumRepository
import com.example.albumsearch.model.database.entities.AlbumEntity
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

/**
 * [ViewModel] of [album] details
 *
 * @property album
 */

class AlbumDetailViewModel
@AssistedInject
constructor(
    private val repository: AlbumRepository,
    @Assisted
    val album: AlbumEntity
) : BaseViewModel() {

    /**
     * Track list to display
     */
    val tracks = repository.getTracks(album)

    /**
     * URL of the image to be displayed as a cover art.
     * It being a separate field is deliberate: when the "refresh" is clicked, we reassign it,
     * which initiates the image reload in the view bound to this url
     */
    val coverArtUrl = MutableLiveData(album.highResolutionCoverArtUrl)

    init {
        refreshTrackList(isForced = false)
    }

    /**
     * Requests [repository] to fetch track list of the [album]. [repository] can be forced to do so
     * even if it's not necessary by passing true to [isForced].
     */
    private fun refreshTrackList(isForced: Boolean) {
        viewModelScope.launch {
            try {
                repository.fetchTracksIfRequired(album, isForced)
            } catch (exception: Exception) {
                _toastMessage.value = R.string.couldnt_load_track_list
            }
        }
    }

    /** Handles refresh button click */
    fun onRefreshClicked() {
        coverArtUrl.postValue(coverArtUrl.value)
        refreshTrackList(isForced = true)
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(album: AlbumEntity): AlbumDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            album: AlbumEntity
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(album) as T
            }
        }
    }

}