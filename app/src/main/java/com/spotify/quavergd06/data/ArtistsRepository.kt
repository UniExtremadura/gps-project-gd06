package com.spotify.quavergd06.data

import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.api.SpotifyApiService
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.database.dao.ArtistDAO
import java.sql.RowId

class ArtistsRepository (
    private val artistDAO: ArtistDAO, private
    val networkService: SpotifyApiService
) {
    private var lastUpdateTimeMillis: Long = 0L
    val artists = artistDAO.getArtists()
    val artistsAll = artistDAO.getPersonalArtistByTimeRange("long_term")
    val artistsMonths = artistDAO.getPersonalArtistByTimeRange("medium_term")


    suspend fun tryUpdateRecentCache(timeRange: String) {
        if (shouldUpdateCache(timeRange))
            fetchArtists(timeRange)
    }
    private suspend fun fetchArtists(timeRange: String) {
        try {
            val artists = networkService.loadTopArtists(timeRange).body()?.artistItems?.map(ArtistItem::toArtist) ?: emptyList()

            // Set position and time range for different types of tracks
            setArtistPosition(artists)
            setArtistTimeRange(artists, timeRange)

            artistDAO.insertAllArtist(artists)
            lastUpdateTimeMillis = System.currentTimeMillis ()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun fetchArtistDetail(artistId: String) : Artist {
        var artist : Artist
        try {
            artist = networkService.loadArtist(artistId).body()?.toArtist()!!
            artistDAO.insertSingleArtist(artist)
            lastUpdateTimeMillis = System.currentTimeMillis ()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return artist
    }


    private fun setArtistPosition(tracks: List<Artist>) {
        tracks.forEachIndexed { index, artist ->
            artist.position = index + 1
        }
    }

    private fun setArtistTimeRange(tracks: List<Artist>, timeRange: String) {
        tracks.forEach { artist ->
            artist.timeRange = timeRange
        }
    }

    private suspend fun shouldUpdateCache(timeRange: String): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || artistDAO.getNumberOfPersonalArtist(timeRange) == 0L
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 60000
    }
}