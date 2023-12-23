package com.spotify.quavergd06.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spotify.quavergd06.data.model.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser() : LiveData<User>

    @Query("SELECT count(*) FROM user")
    fun getNumberOfUsers() : Long

    @Query("DELETE FROM user WHERE userId = 1")
    suspend fun deleteUser()
}