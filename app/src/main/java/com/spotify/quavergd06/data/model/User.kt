package com.spotify.quavergd06.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.spotify.quavergd06.data.api.ApiImage
import com.spotify.quavergd06.database.StringListWrapper
import java.io.Serializable

data class User(
    var userId: String?,
    val name: String = "",
    var token: String = "",

) : Serializable
