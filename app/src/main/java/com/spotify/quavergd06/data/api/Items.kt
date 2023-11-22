package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName


data class Items (

    @SerializedName("available_markets" ) var availableMarkets : ArrayList<String>  = arrayListOf(),
    @SerializedName("disc_number"       ) var discNumber       : Int?               = null,
    @SerializedName("duration_ms"       ) var durationMs       : Int?               = null,
    @SerializedName("explicit"          ) var explicit         : Boolean?           = null,
    @SerializedName("href"              ) var href             : String?            = null,
    @SerializedName("id"                ) var id               : String?            = null,
    @SerializedName("name"              ) var name             : String?            = null,
    @SerializedName("popularity"        ) var popularity       : Int?               = null,
    @SerializedName("preview_url"       ) var previewUrl       : String?            = null,
    @SerializedName("track_number"      ) var trackNumber      : Int?               = null,
    @SerializedName("type"              ) var type             : String?            = null,
    @SerializedName("uri"               ) var uri              : String?            = null,
    @SerializedName("is_local"          ) var isLocal          : Boolean?           = null

)