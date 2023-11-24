package com.spotify.quavergd06.data.api

import com.google.gson.annotations.SerializedName

class TopGlobalApiResponse(
    @SerializedName("tracks"        ) var tracks        : TopGlobalTracks?           = TopGlobalTracks(),
)