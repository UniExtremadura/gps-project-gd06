package com.spotify.quavergd06.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spotify.quavergd06.data.model.Track

interface TrackDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTracks(track: Track): Long

    @Query("SELECT * FROM track")
    suspend fun getTopTracks(): List<Track>

    @Query("DELETE FROM track WHERE trackId = :id")
    suspend fun deleteTrack(id: Long)
}