package com.example.albumsearch.model.network.dto

/**
 * ITunes API response
 *
 * @property resultCount amount of results returned
 * @property results
 */
data class Response(
    val resultCount: Int,
    val results: List<Album>
)