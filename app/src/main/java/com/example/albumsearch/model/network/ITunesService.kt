package com.example.albumsearch.model.network

import com.example.albumsearch.model.network.dto.Album
import com.example.albumsearch.model.network.dto.ENTITY_TYPE_TRACK
import com.example.albumsearch.model.network.dto.LookupEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Provides access to ITunes API
 */
object ITunesService {

    /**
     * Base URL of the API service
     */
    private const val BASE_URL = "https://itunes.apple.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val api = retrofit.create(Service::class.java)

    /**
     * Performs a search with passed [term] and returns list of [Album]
     *
     * @param term
     * @return
     */
    suspend fun searchAlbums(term: String): List<Album> {
        return api.search(term, "album").results
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
        return api.getAlbumLookup(albumId).results.filter { it.type == ENTITY_TYPE_TRACK }
    }

}