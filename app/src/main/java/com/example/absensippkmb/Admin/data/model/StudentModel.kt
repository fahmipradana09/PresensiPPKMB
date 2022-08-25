package com.example.absensippkmb.Admin.data.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentModel(
    @PrimaryKey
    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("nim")
    val nim: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

):Parcelable