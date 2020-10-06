package com.example.albumsearch.model.network.dto

/**
 * ITunes API response
 *
 * @property resultCount amount of results returned
 * @property results
 */
data class Response<T>(
    val resultCount: Int,
    val results: List<T>
)