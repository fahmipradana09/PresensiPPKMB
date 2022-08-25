package com.example.absensippkmb.Admin.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Attendence")
@Parcelize
data class AttendenceModel(

    @PrimaryKey
    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("title")
    val name: String? = null,

    @field:SerializedName("start")
    val start: String? = null,

    @field:SerializedName("end")
    val end: String? = null,

) : Parcelable
