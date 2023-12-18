package com.spotify.quavergd06.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.Track

@Dao
interface ArtistDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllArtist(artists: List<Artist>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSingleArtist(artists: Artist)

    @Query("SELECT * FROM artist")
    fun getArtists(): LiveData<List<Artist>>

    @Query("SELECT count(*) FROM artist")
    suspend fun getNumberOfArtists(): Long

    @Query("SELECT count(*) FROM artist WHERE timeRange LIKE :timeRange")
    suspend fun getNumberOfPersonalArtist(timeRange: String): Long

    @Query("SELECT * FROM artist WHERE timeRange LIKE :timeRange")
    fun getPersonalArtistByTimeRange(timeRange: String): LiveData<List<Artist>>

    @Query("DELETE FROM artist WHERE artistId = :id")
    suspend fun deleteArtist(id: String)

}