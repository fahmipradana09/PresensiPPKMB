package com.example.absensippkmb.Admin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KafasModel(
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("nim")
    val nim:String?= null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("password")
    val password: String? =null,
    @field:SerializedName("_id")
    val _id: String? = null,
    @field:SerializedName("token")
    val token: String? = null

):Parcelable