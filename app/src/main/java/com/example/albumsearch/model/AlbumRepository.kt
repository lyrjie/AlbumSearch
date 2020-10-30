package com.example.albumsearch.model

import com.example.albumsearch.model.network.ITunesApi
import com.example.albumsearch.model.network.dto.Album
import com.example.albumsearch.model.network.dto.ENTITY_TYPE_TRACK
import com.example.albumsearch.model.network.dto.LookupEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Inject

/**
 * Provides access to ITunes API
 *
 * @property iTunesService API service
 */
class AlbumRepository
@Inject
constructor(private val iTunesService: ITunesApi) {

    /**
     * Performs a search with passed [term] and returns list of [Album]
     *
     * @param term
     * @return
     */
    suspend fun searchAlbums(term: String): List<Album> {
        return iTunesService.search(term, "album").results
    }

    /**
     * Returns songs that belong to the album with id of [albumId]
     *
     * @param albumId
     * @return
     */
    suspend fun getSongs(albumId: Long): List<LookupEntity> {
        // IMPORTANT: ITunes returns bot only songs, but also a containing album even if you
        // specifically ask to only return songs like we do. So we have to further filter it by type
        return iTunesService.getAlbumLookup(albumId).results.filter { it.type == ENTITY_TYPE_TRACK }
    }

}