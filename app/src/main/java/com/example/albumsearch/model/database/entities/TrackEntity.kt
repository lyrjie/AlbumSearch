package com.example.albumsearch.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val albumId: Long?,
    val name: String?,
    val trackNumber: Int?,
    val trackTimeMillis: Long?,
)