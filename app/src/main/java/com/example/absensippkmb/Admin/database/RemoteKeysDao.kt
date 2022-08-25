package com.example.absensippkmb.Admin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.absensippkmb.Admin.database.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKey(id: String?): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}