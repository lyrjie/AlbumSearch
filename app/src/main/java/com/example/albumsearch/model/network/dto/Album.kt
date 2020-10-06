package com.example.albumsearch.model.network.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Representation of Album data.
 *
 * @property id ITunes id
 * @property artistName the name of artist(s)
 * @property name name of an album
 * @property coverArtUrl URL of album cover
 * @property highResolutionCoverArtUrl URL of higher resolution album cover
 */
// We need to parcelize the class since we are going to pass it between fragments
@Parcelize
data class Album(
    @Json(name = "collectionId")
    val id: Long,

    val artistName: String,

    @Json(name = "collectionName")
    val name: String,

    @Json(name = "artworkUrl100")
    val coverArtUrl: String,

    val copyright: String,
    val country: String,
    val releaseDate: Date,

    @Json(name = "primaryGenreName")
    val genre: String
) : Parcelable {

    val highResolutionCoverArtUrl: String
        // coverArtUrl contains the definition of preferred resolution which we can replace to get
        // a higher resolution image
        get() = coverArtUrl.replace("100x100bb", "400x400bb")

}