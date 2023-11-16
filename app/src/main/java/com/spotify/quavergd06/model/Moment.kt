package com.spotify.quavergd06.model

import java.io.Serializable
import java.util.Date

data class Moment(
    val title: String = "",
    val date: Date? = null,
    val description: String = "",
    val image: Int,
) : Serializable
