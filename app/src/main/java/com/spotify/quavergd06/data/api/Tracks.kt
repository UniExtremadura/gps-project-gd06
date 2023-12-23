package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName


data class Tracks (

    @SerializedName("items"    ) var items    : ArrayList<Items> = arrayListOf()

)