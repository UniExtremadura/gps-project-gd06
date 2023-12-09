package com.spotify.quavergd06.data

import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.api.SpotifyApiService
import com.spotify.quavergd06.database.ArtistDAO

class ArtistsRepository (
    private val artistDAO: ArtistDAO, private
    val networkService: SpotifyApiService
) {
    private var lastUpdateTimeMillis: Long = 0L
    val artists = artistDAO.getArtists()

    suspend fun tryUpdateRecentArtistsCache(timeRange: String) {
        if (shouldUpdateShowsCache())
            fetchArtists(timeRange)
    }

    private suspend fun fetchArtists(timeRange: String) {
        try {
            val artists = networkService.loadTopArtists(timeRange).body()?.artistItems?.map { it.toArtist() } ?: emptyList()
            artistDAO.insertAllArtist(artists)
            lastUpdateTimeMillis = System.currentTimeMillis ()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }
    private suspend fun shouldUpdateShowsCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || artistDAO.getNumberOfArtists() == 0L
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000
        @Volatile private var INSTANCE: ArtistsRepository? = null

        fun getInstance(artistDAO: ArtistDAO, spotifyApiService: SpotifyApiService): ArtistsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ArtistsRepository(
                    artistDAO,
                    spotifyApiService
                ).also { INSTANCE = it }
            }
        }
    }
}