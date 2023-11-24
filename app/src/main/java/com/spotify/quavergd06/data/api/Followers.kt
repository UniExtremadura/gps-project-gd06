package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName


data class Followers (

    @SerializedName("href"  ) var href  : String? = null,
    @SerializedName("total" ) var total : Int?    = null

)