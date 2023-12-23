package com.spotify.quavergd06.data.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.spotify.quavergd06.database.Converters
import com.spotify.quavergd06.database.StringListWrapper

@Entity(primaryKeys = ["trackId", "type", "timeRange"])
data class Track(
    val trackId: String,
    val name         : String?            = null,
    val album   : String?                  = null,
    var artistId       : String            ?= null,
    var artistName     : String            ?= null,
    var position: Int? = null,
    var timeRange: String,
    var type: String,

    @TypeConverters(Converters::class)
    val imageUrls: StringListWrapper = StringListWrapper(arrayListOf())
)