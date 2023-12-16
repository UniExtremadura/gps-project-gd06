package com.spotify.quavergd06.data

import androidx.lifecycle.LiveData
import com.spotify.quavergd06.data.model.Moment
import com.spotify.quavergd06.database.dao.MomentDAO

class MomentsRepository (private val momentDAO: MomentDAO) {

    suspend fun fetchMomentDetail(momentId: Long): Moment {
        return momentDAO.getMoment(momentId)
    }

    val moments = momentDAO.getAllMoments()

    suspend fun deleteMoment(momentId: Long) {
        return momentDAO.deleteMoment(momentId)
    }

    fun getFilteredMoments(momentTitle: String): LiveData<List<Moment>> {
        return momentDAO.getFilteredMoments(momentTitle)
    }

}