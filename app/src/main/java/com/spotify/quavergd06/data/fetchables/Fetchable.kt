package com.spotify.quavergd06.data.fetchables

import com.spotify.quavergd06.data.model.StatsItem

interface Fetchable {
    suspend fun fetch(): List<StatsItem>
}
