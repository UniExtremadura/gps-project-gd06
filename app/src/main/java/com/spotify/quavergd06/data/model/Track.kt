package com.spotify.quavergd06.data.model

data class Track(
    val id: String,
    val name         : String?            = null,
    val album   : String?                    = null,
    var artist       : Artist            ?= null,
    val imageUrls    : ArrayList<String> ?= null,
)