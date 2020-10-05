package com.example.albumsearch.model.network

import com.example.albumsearch.model.network.dto.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API description
 */
interface Service {

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
    ): Response
}
