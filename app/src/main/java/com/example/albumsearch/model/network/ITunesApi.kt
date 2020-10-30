package com.example.albumsearch.model.network

import com.example.albumsearch.model.network.dto.Album
import com.example.albumsearch.model.network.dto.Response
import com.example.albumsearch.model.network.dto.LookupEntity
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API description
 */
interface ITunesApi {

    companion object {
        /** Base URL of the API endpoints */
        const val BASE_URL = "https://itunes.apple.com/"
    }

    /**
     * Performs a search
     *
     * @param term words to search by
     * @param entity type of entities to search for
     * @return [Response] of the search results
     */
    @GET("search")
    suspend fun search(
        @Query("term")
        term: String,

        // "entity" could have been an enum, but since we are only searching for albums, there's
        // no need for over-engineering
        @Query("entity")
        entity: String = "album"
    ): Response<Album>

    /**
     * Loads all entities associated with album with id [albumId]
     *
     * @param albumId
     * @param entity
     * @return
     */
    @GET("lookup")
    suspend fun getAlbumLookup(
        @Query("id")
        albumId: Long,

        @Query("entity")
        entity: String = "song"
    ): Response<LookupEntity>
}
