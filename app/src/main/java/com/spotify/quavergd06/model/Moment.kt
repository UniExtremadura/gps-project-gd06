package com.spotify.quavergd06.model

import java.io.Serializable
import java.util.Date

data class Moment(
    val title: String = "",
    val date: Date? = null,
    val songID: String = "",
    val songTitle: String = "",
    val image: Int,
    val location: String = "",
    val latitude: Double,
    val longitude: Double
) : Serializable

