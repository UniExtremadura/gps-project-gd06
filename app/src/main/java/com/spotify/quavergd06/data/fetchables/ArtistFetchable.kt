package com.spotify.quavergd06.data.fetchables

import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.toArtist
import com.spotify.quavergd06.data.toStatsItem

class ArtistFetchable : Fetchable {
    override suspend fun fetch(): List<StatsItem> {
        val apiArtists = getNetworkService().loadTopArtists("medium_term").body()?.artistItems ?: emptyList()
        return apiArtists.map(ArtistItem::toArtist).map(Artist::toStatsItem)
    }
    override suspend fun fetch(timePeriod: String): List<StatsItem> {
        val apiArtists = getNetworkService().loadTopArtists(timePeriod).body()?.artistItems ?: emptyList()
        return apiArtists.map(ArtistItem::toArtist).map(Artist::toStatsItem)
    }
}
