package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

class TopTrackResponse(

    @SerializedName("items") var trackItems: ArrayList<TrackItem> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("href") var href: String? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null

)
