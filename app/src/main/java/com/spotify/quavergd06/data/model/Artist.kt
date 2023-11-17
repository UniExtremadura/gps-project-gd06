package com.spotify.quavergd06.data.model

import android.media.Image

data class Artist(
    val id: String,
    val name         : String?           = null,
    val popularity   : Int?              = null,
    var genres       : ArrayList<String> = arrayListOf(),
    val imageUrls    : ArrayList<String>?= null,
)
