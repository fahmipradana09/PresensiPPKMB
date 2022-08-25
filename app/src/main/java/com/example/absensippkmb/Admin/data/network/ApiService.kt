package com.example.absensippkmb.Admin.data.network


import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("{{URL}}/kafas/signin")
    fun kafasLogin(
        @Body user: Map<String, String>
    ) : Call<DataResponseKafas>

    @POST("{{URL}}/kafas/add")
    fun kafasRegister(
        @Body user: Map<String, String>
    ) : Call<DataResponseKafas>

    @GET("{{URL}}/kafas/all")
    fun getUserAbsensi(
        @Query("location") location: Int = 0,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ) : Call<DataResponseKafas>

    @Multipart
    @POST("stories")
    fun postUserStory(
        @Part photo : MultipartBody.Part,
        @Part("lat") lat: Float? = null,
        @Part("lon") lon: Float? = null,
    ) : Call<DataResponseKafas>
}