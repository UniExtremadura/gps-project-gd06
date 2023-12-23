package com.spotify.quavergd06.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.Moment
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.model.User
import com.spotify.quavergd06.database.dao.ArtistDAO
import com.spotify.quavergd06.database.dao.MomentDAO
import com.spotify.quavergd06.database.dao.TrackDAO
import com.spotify.quavergd06.database.dao.UserDAO

@Database(entities = [Moment::class, Artist::class, Track::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuaverDatabase : RoomDatabase() {
    abstract fun momentDAO(): MomentDAO
    abstract fun artistDAO(): ArtistDAO
    abstract fun trackDAO(): TrackDAO
    abstract fun userDAO(): UserDAO
    companion object {
        private var INSTANCE: QuaverDatabase? = null

        fun getInstance(context: Context): QuaverDatabase {
            if (INSTANCE == null) {
                synchronized(QuaverDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        QuaverDatabase::class.java,
                        "quaver_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}