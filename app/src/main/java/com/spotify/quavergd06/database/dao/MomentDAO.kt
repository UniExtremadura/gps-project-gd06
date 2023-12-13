package com.spotify.quavergd06.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spotify.quavergd06.data.model.Moment

@Dao
interface MomentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoment(moment: Moment): Long

    @Query("SELECT * FROM moment WHERE momentId = :id LIMIT 1")
    suspend fun getMoment(id: Long) : Moment

    @Query("SELECT * FROM moment ORDER BY date DESC")
    fun getAllMoments(): LiveData<List<Moment>>

    //Delete a moment
    @Query("DELETE FROM moment WHERE momentId = :id")
    suspend fun deleteMoment(id: Long)
}