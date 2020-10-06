package com.example.albumsearch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.albumsearch.model.network.dto.Album

/**
 * [ViewModel] of [album] details
 *
 * @property album
 */
class AlbumDetailViewModel(val album: Album) : ViewModel()