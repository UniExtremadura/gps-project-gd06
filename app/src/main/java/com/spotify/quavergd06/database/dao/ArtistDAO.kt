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
    suspend fun insertArtist(artists: Artist)


    @Query("SELECT * FROM artist")
    fun getArtists(): LiveData<List<Artist>>

    @Query("SELECT count(*) FROM artist")
    suspend fun getNumberOfArtists(): Long

    @Query("DELETE FROM artist WHERE artistId = :id")
    suspend fun deleteArtist(id: String)

}