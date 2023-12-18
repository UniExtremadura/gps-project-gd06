package com.spotify.quavergd06.data

import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.api.SpotifyApiService
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.database.dao.TrackDAO

class TracksRepository (
    private val tracksDAO: TrackDAO,
    private val networkService: SpotifyApiService
){
    private var lastUpdateTimeMillis: Long = 0L
    val tracks = tracksDAO.getTopTracks()

    suspend fun tryUpdateCache(timeRange: String) {
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

    suspend fun fetchTrackDetail(trackId: String) : Track {
        var track : Track
        try {
            track = networkService.loadTrack(trackId).body()?.toTrack()!!
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return track
    }

    private suspend fun shouldUpdateCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || tracksDAO.getNumberOfPersonalTracks() == 0L
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