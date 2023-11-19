package com.spotify.quavergd06.data.model

import java.io.Serializable

data class StatsItem (
    val id           : String?            = "33",

    val name         : String?            = null,
    val imageUrls    : ArrayList<String>? = null,

    // Artist
    val genres       : ArrayList<String>? = arrayListOf(),
    val popularity   : Int?               = null,

    // Track
    val album        : String?            = null,
    val artist       : Artist?            = null,

    ) : Serializable