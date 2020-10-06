package com.example.albumsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.albumsearch.R
import com.example.albumsearch.model.network.ITunesService
import com.example.albumsearch.model.network.dto.Album
import com.example.albumsearch.model.network.dto.LookupEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * [ViewModel] of [album] details
 *
 * @property album
 */
class AlbumDetailViewModel(val album: Album) : BaseViewModel() {

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
                _tracks.value = ITunesService.getSongs(album.id).sortedBy { it.trackNumber }
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
}