package com.example.absensippkmb.Admin.data.network


import com.example.absensippkmb.Admin.data.model.KafasModel
import com.google.gson.annotations.SerializedName


data class DataResponseKafas(

    @field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("data")
	val data: KafasModel? = null,

    @field:SerializedName("transaction")
	val transaction: ArrayList<KafasModel>? = null,



    )
