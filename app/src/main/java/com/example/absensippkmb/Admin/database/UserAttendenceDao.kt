package com.example.absensippkmb.Admin.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.absensippkmb.Admin.data.model.AttendenceModel

@Dao
interface UserAttendenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAttedenceModel(quote: List<AttendenceModel>)

    @Query("SELECT * FROM Attendence")
    fun getAllUserAttencence(): List<AttendenceModel>

    @Query("DELETE FROM Attendence")
    suspend fun deleteAllAttedence()
}