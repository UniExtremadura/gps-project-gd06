package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

class TopTracksResponse(

    @SerializedName("items") var trackItems: ArrayList<TrackItem> = arrayListOf()

)