package com.spotify.quavergd06.data.fetchables

import android.os.Parcelable
import com.spotify.quavergd06.data.model.StatsItem
import java.io.Serializable

interface Fetchable : Serializable{
    suspend fun fetch(): List<StatsItem>
    suspend fun fetch(timePeriod: String): List<StatsItem>
}
