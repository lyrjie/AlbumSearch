package com.example.albumsearch.model.network.dto

import com.squareup.moshi.Json

const val ENTITY_TYPE_TRACK = "track"

/**
 * Entity data (entity could be a collection (album) or song
 *
 * @property name track name
 * @property trackNumber number in the album
 * @property trackTimeMillis track duration in milliseconds
 * @property type entity type
 */
data class LookupEntity(
    @Json(name = "trackName")
    val name: String?,

    val trackNumber: Int?,
    val trackTimeMillis: Long?,

    @Json(name = "wrapperType")
    val type: String
)