package com.spotify.quavergd06.model

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
    val image: Int,
    val location: String = "",
    val latitude: Double,
    val longitude: Double,
    val imageBitmap: Bitmap? = null
) : Serializable

