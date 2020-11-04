package com.example.albumsearch.model.network.dto

import com.squareup.moshi.Json


/**
 * Entity data (entity could be a collection (album) or a song)
 *
 * @property name entity name
 * @property trackId iTunes id of the track (track-only)
 * @property trackNumber number in the collection (track-only)
 * @property trackTimeMillis duration in milliseconds (track-only)
 * @property type entity type
 */
data class LookupEntityDto(
    @Json(name = "trackName")
    val name: String?,

    @Json(name = "collectionId")
    val albumId: Long?,
    val trackId: Long?,
    val trackNumber: Int?,
    val trackTimeMillis: Long?,

    @Json(name = "wrapperType")
    val type: String
) {
    companion object {
        private const val ENTITY_TYPE_TRACK = "track"
    }

    val isTrack = type == ENTITY_TYPE_TRACK
}

// DISCLAIMER: It being "LookupEntity" and not "Song" is related to ITunes for some reason also
// returning album in a JSON array along with songs, even when you specifically ask for songs.
// The proper way to do that would be to implement Song and Album as subclasses of Entity and write
// your own Moshi mapper which decides which subclass to build on a per case basis. But it really
// felt like an overkill for a small task.
