package com.spotify.quavergd06.data.api
import com.google.gson.annotations.SerializedName
data class UserProfileInfoResponse (

    @SerializedName("country"          ) var country         : String?           = null,
    @SerializedName("display_name"     ) var displayName     : String?           = null,
    @SerializedName("email"            ) var email           : String?           = null,
    @SerializedName("external_urls"    ) var externalUrls    : ExternalUrls?     = ExternalUrls(),
    @SerializedName("followers"        ) var followers       : Followers?        = Followers(),
    @SerializedName("href"             ) var href            : String?           = null,
    @SerializedName("id"               ) var id              : String?           = null,
    @SerializedName("images"           ) var images          : ArrayList<ApiImage> = arrayListOf(),
    @SerializedName("product"          ) var product         : String?           = null,
    @SerializedName("type"             ) var type            : String?           = null,
    @SerializedName("uri"              ) var uri             : String?           = null

)

