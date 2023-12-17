package com.spotify.quavergd06.util

import android.content.Context
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.data.ArtistsRepository
import com.spotify.quavergd06.data.MomentsRepository
import com.spotify.quavergd06.data.TracksRepository
import com.spotify.quavergd06.database.QuaverDatabase

class AppContainer(context: Context?) {

    private val networkService = getNetworkService()
    private val db = QuaverDatabase.getInstance(context!!)

    val momentsRepository = MomentsRepository(db.momentDAO())
    val tracksRepository = TracksRepository(db.trackDAO(), networkService)
    val artistRepository = ArtistsRepository(db.artistDAO(), networkService)

}