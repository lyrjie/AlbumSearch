package com.example.albumsearch.model.network

import com.example.albumsearch.model.network.dto.Album
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

    suspend fun searchAlbums(term: String): List<Album> {
        return api.search(term, "album").results
    }

}