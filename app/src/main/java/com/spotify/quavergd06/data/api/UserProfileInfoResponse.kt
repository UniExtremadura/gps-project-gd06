package com.spotify.quavergd06.data.api
import com.google.gson.annotations.SerializedName
data class UserProfileInfoResponse (

    @SerializedName("display_name"     ) var displayName     : String?           = null,
    @SerializedName("id"               ) var id              : String?           = null,
    @SerializedName("images"           ) var images          : ArrayList<ApiImage> = arrayListOf()

)