package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

data class TopGlobalTracks (

    @SerializedName("items"    ) var items    : ArrayList<ItemsHistory> = arrayListOf()

)