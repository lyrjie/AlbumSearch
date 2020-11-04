package com.example.albumsearch.model

import androidx.lifecycle.LiveData
import com.example.albumsearch.model.database.AlbumDao
import com.example.albumsearch.model.database.entities.AlbumEntity
import com.example.albumsearch.model.database.entities.TrackEntity
import com.example.albumsearch.model.network.ITunesApi
import javax.inject.Inject

/**
 * Repository providing access to album data
 *
 * @property iTunesService album API service
 * @property albumDao album persistence DTO
 */
class AlbumRepository
@Inject
constructor(
    private val iTunesService: ITunesApi,
    private val albumDao: AlbumDao
) {

    /**
     * Returns [LiveData] with list of [AlbumEntity] returned by searching with [term]
     *
     * @param term keywords to search by
     */
    fun getFilteredAlbums(term: String): LiveData<List<AlbumEntity>> {
        return albumDao.searchAlbums(term)
    }

    /**
     * Updates repository with external search results
     *
     * @param term keywords to search by
     */
    suspend fun performSearch(term: String) {
        val searchResults = iTunesService.search(term, "album").results.map { it.toDomain() }
        albumDao.addAlbums(searchResults)
    }

    /**
     * Returns the tracks of the [album]
     *
     * @param album album to return tracks for
     */
    fun getTracks(album: AlbumEntity): LiveData<List<TrackEntity>> {
        return albumDao.getTracksByAlbumId(album.id)
    }

    /**
     * Fetches the track list for album if it wasn't fetched already
     * (this check can be bypassed by passing `true` to [isForced])
     *
     * @param album album to fetch songs for
     * @param isForced if true, fetches the track list even if it was already loaded
     */
    suspend fun fetchTracksIfRequired(album: AlbumEntity, isForced: Boolean = false) {
        if (isTrackListPersisted(album) || isForced) {
            val tracks = iTunesService.getAlbumLookup(album.id).results
                .filter { it.isTrack }
                .mapNotNull { it.toDomainTrack() }
            albumDao.addTracks(tracks)
        }
    }

    /**
     * Returns `true` if the tracks for [album] are persisted
     *
     * @param album album to perform check for
     */
    private suspend fun isTrackListPersisted(album: AlbumEntity): Boolean {
        return albumDao.getTrackCountByAlbumId(album.id) == 0L
    }

}