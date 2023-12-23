package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName


data class TopArtistsResponse (

    @SerializedName("items"    ) var artistItems    : ArrayList<ArtistItem> = arrayListOf()

)