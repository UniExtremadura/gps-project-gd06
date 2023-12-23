package com.spotify.quavergd06.util

import android.content.Context
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.data.ArtistsRepository
import com.spotify.quavergd06.data.GlobalTopTracksRepository
import com.spotify.quavergd06.data.HistoryRepository
import com.spotify.quavergd06.data.MomentsRepository
import com.spotify.quavergd06.data.TracksRepository
import com.spotify.quavergd06.data.UserRepository
import com.spotify.quavergd06.database.QuaverDatabase

class AppContainer(context: Context?) {

    private val networkService = getNetworkService()
    private val db = QuaverDatabase.getInstance(context!!)

    val momentsRepository = MomentsRepository(db.momentDAO())
    val artistRepository = ArtistsRepository(db.artistDAO(), networkService)
    val userRepository = UserRepository(db.userDAO(), networkService)

    val tracksRepository = TracksRepository(db.trackDAO(), networkService)
    val globalTopTracksRepository = GlobalTopTracksRepository(db.trackDAO(), networkService)
    val historyRepository = HistoryRepository(db.trackDAO(), networkService)

    fun clearAll() {
        db.clearAllTables()
    }
}