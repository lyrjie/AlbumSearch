package com.example.albumsearch.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.albumsearch.model.database.entities.AlbumEntity
import com.example.albumsearch.model.database.entities.TrackEntity

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albumentity")
    fun getAlbums(): LiveData<List<AlbumEntity>>

    @Query("SELECT * FROM albumentity WHERE name LIKE '%' || :query || '%' OR artistName LIKE '%' || :query || '%' ORDER BY name")
    fun searchAlbums(query: String): LiveData<List<AlbumEntity>>

    @Query("SELECT * FROM trackentity WHERE albumId = :albumId ORDER BY trackNumber")
    fun getTracksByAlbumId(albumId: Long): LiveData<List<TrackEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlbums(album: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTracks(tracks: List<TrackEntity>)

    @Update
    suspend fun updateAlbum(album: AlbumEntity)

    @Query("SELECT count(*) FROM trackentity WHERE albumId = :albumId")
    suspend fun getTrackCountByAlbumId(albumId: Long): Long

}