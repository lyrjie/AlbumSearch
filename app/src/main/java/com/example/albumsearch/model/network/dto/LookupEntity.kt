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

// DISCLAIMER: It being "LookupEntity" and not "Song" is related to ITunes for some reason also
// returning album in a JSON array along with songs, even when you specifically ask for songs.
// The proper way to do that would be to implement Song and Album as subclasses of Entity and write
// your own Moshi mapper which decides which subclass to build on a per case basis. But it really
// felt like an overkill for a small task.
