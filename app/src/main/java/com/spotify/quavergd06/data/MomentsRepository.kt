package com.spotify.quavergd06.data

import com.spotify.quavergd06.database.MomentDAO

class MomentsRepository private constructor (private val momentDAO: MomentDAO) {

    val moments = momentDAO.getAllMoments()

    companion object {
        @Volatile private var INSTANCE: MomentsRepository? = null

        fun getInstance(momentDAO: MomentDAO): MomentsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MomentsRepository(
                    momentDAO
                ).also { INSTANCE = it }
            }
        }
    }
}