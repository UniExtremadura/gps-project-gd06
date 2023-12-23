package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

data class ItemsHistory(

    @SerializedName("track"     ) var track    : TrackItem?  = TrackItem()

)