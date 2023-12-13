package com.spotify.quavergd06.data

import com.spotify.quavergd06.data.model.Moment
import com.spotify.quavergd06.database.dao.MomentDAO

class MomentsRepository private constructor (private val momentDAO: MomentDAO) {

    suspend fun fetchMomentDetail(momentId: Long): Moment {
        return momentDAO.getMoment(momentId)
    }

    val moments = momentDAO.getAllMoments()

    suspend fun deleteMoment(momentId: Long) {
        return momentDAO.deleteMoment(momentId)
    }
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