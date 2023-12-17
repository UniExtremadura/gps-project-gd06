package com.spotify.quavergd06.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spotify.quavergd06.data.model.Track

@Dao
interface TrackDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTracks(tracks: List<Track>)

    @Query("SELECT * FROM track WHERE type='personal' ORDER BY position ASC")
    fun getTopTracks(): LiveData<List<Track>>

    @Query("SELECT * FROM track WHERE type='global' ORDER BY position ASC")
    fun getTopGlobalTracks(): LiveData<List<Track>>

    @Query("SELECT * FROM track WHERE type='recent' ORDER BY position ASC")
    fun getHistory(): LiveData<List<Track>>

    @Query("SELECT count(*) FROM track WHERE type='personal'")
    suspend fun getNumberOfPersonalTracks(): Long

    @Query("SELECT count(*) FROM track WHERE type='global'")
    suspend fun getNumberOfGlobalTracks(): Long

    @Query("SELECT count(*) FROM track WHERE type='recent'")
    suspend fun getNumberOfHistoryTracks(): Long

    @Query("DELETE FROM track WHERE trackId = :id")
    suspend fun deleteTrack(id: Long)
}