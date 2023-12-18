package com.spotify.quavergd06.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.spotify.quavergd06.database.Converters
import com.spotify.quavergd06.database.StringListWrapper
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 1,
    var userId: String = "",
    val name: String = "",
    var token: String = "",
    @TypeConverters(Converters::class)
    val profileImages: StringListWrapper = StringListWrapper(arrayListOf())
    ) : Serializable
