package com.example.albumsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.albumsearch.R
import com.example.albumsearch.model.AlbumRepository
import com.example.albumsearch.model.network.dto.Album
import com.example.albumsearch.model.network.dto.LookupEntity
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    val album: Album
) : BaseViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    /**
     * Track list to display
     */
    private val _tracks = MutableLiveData<List<LookupEntity>>()
    val tracks: LiveData<List<LookupEntity>>
        get() = _tracks

    init {
        getTrackList()
    }

    /**
     * Updates [_tracks] with the track list loaded with [album]'s id
     *
     */
    private fun getTrackList() {
        coroutineScope.launch {
            try {
                _tracks.value = repository.getSongs(album.id).sortedBy { it.trackNumber }
            } catch (exception: Exception) {
                _toastMessage.value = R.string.couldnt_load_track_list
                _tracks.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        // Cancel pending coroutines
        job.cancel()
    }

    /** Handles refresh button click */
    fun onRefreshClicked() {
        getTrackList()
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(album: Album): AlbumDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            album: Album
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(album) as T
            }
        }
    }

}