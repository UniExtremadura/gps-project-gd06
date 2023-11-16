package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

data class SerializedImage (
    @SerializedName("url"    ) var url    : String? = null,
    @SerializedName("height" ) var height : Int?    = null,
    @SerializedName("width"  ) var width  : Int?    = null
)