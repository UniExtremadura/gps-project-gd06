package com.spotify.quavergd06.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.spotify.quavergd06.model.Moment

@Dao
interface MomentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoment(moment: Moment): Long

    @Query("SELECT * FROM moment")
    suspend fun getAllMoments(): List<Moment>

    //Delete a moment
    @Query("DELETE FROM moment WHERE momentId = :id")
    suspend fun deleteMoment(id: Long)
}