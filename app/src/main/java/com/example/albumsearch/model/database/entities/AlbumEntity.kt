package com.example.albumsearch.model.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class AlbumEntity(
    @PrimaryKey
    val id: Long,
    val artistName: String,
    val name: String,
    val coverArtUrl: String,
    val highResolutionCoverArtUrl: String,
    val copyright: String?,
    val releaseDate: Date?,
    val genre: String?
) : Parcelable