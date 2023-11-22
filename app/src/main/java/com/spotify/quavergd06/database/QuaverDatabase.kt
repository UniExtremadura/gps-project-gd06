package com.spotify.quavergd06.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spotify.quavergd06.data.model.Moment

@Database(entities = [Moment::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuaverDatabase : RoomDatabase() {
    abstract fun momentDAO(): MomentDAO

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