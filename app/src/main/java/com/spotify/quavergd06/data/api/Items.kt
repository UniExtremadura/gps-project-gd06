package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName


data class Items (

    @SerializedName("id"                ) var id               : String?            = null,
    @SerializedName("name"              ) var name             : String?            = null,
    @SerializedName("type"              ) var type             : String?          = null

)