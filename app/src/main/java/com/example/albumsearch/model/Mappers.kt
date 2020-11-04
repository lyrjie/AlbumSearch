package com.example.albumsearch.model

import com.example.albumsearch.model.database.entities.AlbumEntity
import com.example.albumsearch.model.database.entities.TrackEntity
import com.example.albumsearch.model.network.ITunesApi
import com.example.albumsearch.model.network.dto.AlbumDto
import com.example.albumsearch.model.network.dto.LookupEntityDto


fun AlbumDto.toDomain(): AlbumEntity {
    return AlbumEntity(
        id = this.id,
        artistName = this.artistName,
        name = this.name,
        coverArtUrl = this.coverArtUrl,
        highResolutionCoverArtUrl = getHighResolutionCoverArtUrl(this.coverArtUrl),
        copyright = this.copyright,
        releaseDate = this.releaseDate,
        genre = this.genre
    )
}

/**
 * Returns url of the higher resolution version of the image at [coverArtUrl]
 *
 * @param coverArtUrl url to "upscale"
 */
private fun getHighResolutionCoverArtUrl(coverArtUrl: String): String {
    // coverArtUrl contains the definition of preferred resolution which we can replace to get
    // a higher resolution image
    return coverArtUrl.replace(
        ITunesApi.IMAGE_URL_RESOLUTION_DEFAULT,
        ITunesApi.IMAGE_URL_RESOLUTION_HIGH
    )
}

fun LookupEntityDto.toDomainTrack(): TrackEntity? {
    if (!isTrack) return null
    return TrackEntity(
        id = this.trackId ?: 0L,
        albumId = this.albumId,
        name = this.name,
        trackNumber = this.trackNumber,
        trackTimeMillis = this.trackTimeMillis
    )
}