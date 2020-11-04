package com.example.albumsearch.model.network.dto

import com.squareup.moshi.Json
import java.util.*

/**
 * Representation of Album data.
 *
 * @property id ITunes id
 * @property artistName the name of artist(s)
 * @property name name of an album
 * @property coverArtUrl URL of album cover
 * @property copyright copyright text
 * @property releaseDate date of the album release
 * @property genre primary genre
 */
// We need to parcelize the class since we are going to pass it between fragments
data class AlbumDto(
    @Json(name = "collectionId")
    val id: Long,

    val artistName: String,

    @Json(name = "collectionName")
    val name: String,

    @Json(name = "artworkUrl100")
    val coverArtUrl: String,

    val copyright: String?,
    val releaseDate: Date?,

    @Json(name = "primaryGenreName")
    val genre: String?
)