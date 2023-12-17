package com.spotify.quavergd06.data

import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.api.SpotifyApiService
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.database.dao.TrackDAO

class TracksRepository (
    private val tracksDAO: TrackDAO, private
    val networkService: SpotifyApiService
){
    private var lastUpdateTimeMillis: Long = 0L
    val tracks = tracksDAO.getTopTracks()
    val tracksGlobalTop = tracksDAO.getTopGlobalTracks()
    val tracksHistory = tracksDAO.getHistory()

    suspend fun tryUpdateRecentPersonalTopTracksCache(timeRange: String) {
        if (shouldUpdateCache())
            fetchTracks(timeRange)
    }

    private suspend fun fetchTracks(timeRange: String) {
        try {
            val tracks = networkService.loadTopTracks(timeRange).body()?.trackItems?.map(TrackItem::toTrack) ?: emptyList()

            // Set position and time range for different types of tracks
            setType(tracks, "personal")
            setTrackPosition(tracks)
            setTrackTimeRange(tracks, timeRange)

            tracksDAO.insertAllTracks(tracks)
            lastUpdateTimeMillis = System.currentTimeMillis ()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun tryUpdateGlobalTopTracksCache() {
        if (shouldUpdateCache())
            fetchGlobalTopTracks()
    }
    private suspend fun fetchGlobalTopTracks() {
        try {
            val tracks = networkService.loadTopGlobal().body()?.tracks?.items?.map {it.track!!}?.map(TrackItem::toTrack) ?: emptyList()

            // Set position and time range for different types of tracks
            setType(tracks, "global")
            setTrackPosition(tracks)
            setTrackTimeRange(tracks, "short_term")

            tracksDAO.insertAllTracks(tracks)
            lastUpdateTimeMillis = System.currentTimeMillis ()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun tryUpdateHistoryCache() {
        if (shouldUpdateCache())
            fetchRecentTracks()
    }

    private suspend fun fetchRecentTracks() {
        try {
            val tracks = networkService.loadHistory().body()?.items?.map {it.track!!}?.map(TrackItem::toTrack) ?: emptyList()

            // Set position and time range for different types of tracks
            setType(tracks, "recent")
            setTrackPosition(tracks)
            setTrackTimeRange(tracks, "short_term")

            tracksDAO.insertAllTracks(tracks)
            lastUpdateTimeMillis = System.currentTimeMillis ()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    private suspend fun shouldUpdateCache(): Boolean {
//        val lastFetchTimeMillis = lastUpdateTimeMillis
//        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
//        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || tracksDAO.getNumberOfTracks() == 0L
        return true
    }

    private fun setType(tracks: List<Track>, type: String) {
        tracks.forEach { track ->
            track.type = type
        }
    }

    private fun setTrackPosition(tracks: List<Track>) {
        tracks.forEachIndexed { index, track ->
            track.position = index + 1
        }
    }

    private fun setTrackTimeRange(tracks: List<Track>, timeRange: String) {
        tracks.forEach { track ->
            track.timeRange = timeRange
        }
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 60000
    }
}