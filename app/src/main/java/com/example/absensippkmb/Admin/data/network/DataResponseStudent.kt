package com.example.absensippkmb.Admin.data.network


import com.example.absensippkmb.Admin.data.model.StudentModel
import com.google.gson.annotations.SerializedName


data class DataResponseStudent(

    @field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("data")
	val data: StudentModel? = null,

    @field:SerializedName("transaction")
	val transaction: ArrayList<StudentModel>? = null,



    )
