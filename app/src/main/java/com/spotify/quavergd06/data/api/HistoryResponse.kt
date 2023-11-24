package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

    @SerializedName("items") var items: ArrayList<ItemsHistory> = arrayListOf(),
    @SerializedName("next") var next: String? = null,
    @SerializedName("cursors") var cursors: Cursors? = Cursors(),
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("href") var href: String? = null

)
