package com.spotify.quavergd06.data.fetchables

import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.data.toTrack

class TrackFetchable : Fetchable {
    override suspend fun fetch(): List<StatsItem> {
        val apiTracks = getNetworkService().loadTopTracks("medium_term").body()?.trackItems ?: emptyList()
        return apiTracks.map(TrackItem::toTrack).map(Track::toStatsItem)
    }
    override suspend fun fetch(timePeriod: String): List<StatsItem> {
        val apiTracks = getNetworkService().loadTopTracks(timePeriod).body()?.trackItems ?: emptyList()
        return apiTracks.map(TrackItem::toTrack).map(Track::toStatsItem)
    }
}