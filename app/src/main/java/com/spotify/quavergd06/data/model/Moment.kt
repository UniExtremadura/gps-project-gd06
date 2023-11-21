package com.spotify.quavergd06.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.Serializable
import java.util.Date

@Entity
data class Moment(
    @PrimaryKey(autoGenerate = true) val momentId: Long?,
    val title: String = "",
    val date: Date? = null,
    val songTitle: String = "",
    val imageURI: String,
    val location: String = "",
    val latitude: Double,
    val longitude: Double,
) : Serializable
