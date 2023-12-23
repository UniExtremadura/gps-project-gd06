package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName


data class ApiAlbum (

    @SerializedName("id"                     ) var id                   : String?            = null,
    @SerializedName("images"                 ) var images               : ArrayList<ApiImage>  = arrayListOf(),
    @SerializedName("name"                   ) var name                 : String?            = null

)