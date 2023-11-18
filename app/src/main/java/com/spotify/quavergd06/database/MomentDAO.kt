package com.spotify.quavergd06.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spotify.quavergd06.model.Moment

@Dao
interface MomentDAO {

    @Insert
    suspend fun insertMoment(moment: Moment): Long
}