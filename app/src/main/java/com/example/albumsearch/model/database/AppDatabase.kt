package com.example.albumsearch.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.albumsearch.model.database.entities.AlbumEntity
import com.example.albumsearch.model.database.entities.TrackEntity

@Database(entities = [AlbumEntity::class, TrackEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}