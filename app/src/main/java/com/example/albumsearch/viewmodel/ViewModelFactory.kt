package com.example.albumsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.albumsearch.model.network.dto.Album

/**
 * Factory for constructing [AlbumDetailViewModel] with [album]
 *
 * @property album
 */
class AlbumDetailViewModelFactory(private val album: Album) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
            return AlbumDetailViewModel(album) as T
        }
        throw IllegalArgumentException("Unsupported ViewModel")
    }
}