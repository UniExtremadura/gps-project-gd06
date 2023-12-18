package com.spotify.quavergd06.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.spotify.quavergd06.database.Converters
import com.spotify.quavergd06.database.StringListWrapper
import java.io.Serializable
import java.text.FieldPosition

@Entity(primaryKeys = ["artistId", "timeRange"])
data class Artist(
    val artistId: String,
    val name: String?,
    val popularity: Int?,
    var timeRange: String,
    var position: Int?,

    @TypeConverters(Converters::class)
    val genres: StringListWrapper = StringListWrapper(arrayListOf()),

    @TypeConverters(Converters::class)
    val imageUrls: StringListWrapper = StringListWrapper(arrayListOf())


) : Serializable
