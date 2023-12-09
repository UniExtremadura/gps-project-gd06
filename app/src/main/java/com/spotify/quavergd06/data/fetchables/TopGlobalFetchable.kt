package com.spotify.quavergd06.data.fetchables

import android.util.Log
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.data.toTrack

class TopGlobalFetchable : Fetchable {
    override suspend fun fetch(): List<StatsItem> {
        var apiTracks = getNetworkService().loadTopGlobal().body()?.tracks!!.items.map { it.track!!}
        return apiTracks.map(TrackItem::toTrack).map(Track::toStatsItem)
    }

    override suspend fun fetch(timePeriod: String): List<StatsItem> {
        Log.d("HistoryFetchable", "fetch($timePeriod) called")
        return fetch()
    }
}