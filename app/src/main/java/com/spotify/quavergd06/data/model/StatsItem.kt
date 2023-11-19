package com.spotify.quavergd06.data.model

import java.io.Serializable

data class StatsItem (
    val name         : String?            = null,
    val imageUrls    : ArrayList<String>? = null,
) : Serializable