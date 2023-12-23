package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName


data class TrackItem (

    @SerializedName("album"             ) var album            : ApiAlbum?             = ApiAlbum(),
    @SerializedName("artists"           ) var artists          : ArrayList<ArtistItem> = arrayListOf(),
    @SerializedName("id"                ) var id               : String?            = null,
    @SerializedName("name"              ) var name             : String?            = null

)