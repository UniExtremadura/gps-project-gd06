package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

    @SerializedName("items") var items: ArrayList<ItemsHistory> = arrayListOf()

)