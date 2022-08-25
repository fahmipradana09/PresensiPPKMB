package com.example.absensippkmb.Admin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.absensippkmb.Admin.data.model.AttendenceModel

@Database(
    entities =
    [AttendenceModel::class, RemoteKeys::class],
    version = 1,
    exportSchema = false)
abstract class UserScanDatabase : RoomDatabase() {

    abstract fun userStoryDao(): UserAttendenceDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: UserScanDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserScanDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserScanDatabase::class.java, "user_story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}