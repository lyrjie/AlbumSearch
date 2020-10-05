package com.example.albumsearch.model.network.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Representation of Album data.
 *
 * @property id ITunes id
 * @property artistName the name of artist(s)
 * @property name name of an album
 * @property coverArtUrl URL of album cover
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
    val coverArtUrl: String
) : Parcelable