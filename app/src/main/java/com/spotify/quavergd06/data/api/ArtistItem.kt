package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

data class ArtistItem (

    @SerializedName("genres"        ) var genres       : ArrayList<String> = arrayListOf(),
    @SerializedName("id"            ) var id           : String?           = null,
    @SerializedName("images"        ) var images       : ArrayList<ApiImage> = arrayListOf(),
    @SerializedName("name"          ) var name         : String?           = null,
    @SerializedName("popularity"    ) var popularity   : Int?              = null
)