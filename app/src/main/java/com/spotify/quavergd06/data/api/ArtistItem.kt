package com.spotify.quavergd06.data.api

import com.example.example.ExternalUrls
import com.example.example.Followers
import com.google.gson.annotations.SerializedName

data class ArtistItem (

    @SerializedName("external_urls" ) var externalUrls : ExternalUrls?     = ExternalUrls(),
    @SerializedName("followers"     ) var followers    : Followers?        = Followers(),
    @SerializedName("genres"        ) var genres       : ArrayList<String> = arrayListOf(),
    @SerializedName("href"          ) var href         : String?           = null,
    @SerializedName("id"            ) var id           : String?           = null,
    @SerializedName("images"        ) var images       : ArrayList<SerializedImage> = arrayListOf(),
    @SerializedName("name"          ) var name         : String?           = null,
    @SerializedName("popularity"    ) var popularity   : Int?              = null,
    @SerializedName("type"          ) var type         : String?           = null,
    @SerializedName("uri"           ) var uri          : String?           = null
)