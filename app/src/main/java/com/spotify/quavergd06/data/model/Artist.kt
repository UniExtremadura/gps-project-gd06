package com.spotify.quavergd06.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.spotify.quavergd06.database.Converters
import com.spotify.quavergd06.database.StringListWrapper
import java.io.Serializable

@Entity
data class Artist(
    @PrimaryKey
    val artistId: String,
    val name: String?,
    val popularity: Int?,

    @TypeConverters(Converters::class)
    val genres: StringListWrapper = StringListWrapper(arrayListOf()),

    @TypeConverters(Converters::class)
    val imageUrls: StringListWrapper = StringListWrapper(arrayListOf())

//    val genres: ArrayList<String> = arrayListOf(),
//
//    val imageUrls: ArrayList<String> = arrayListOf(),

) : Serializable
