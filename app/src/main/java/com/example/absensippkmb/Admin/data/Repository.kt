package com.example.absensippkmb.Admin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.absensippkmb.Admin.data.network.ApiInterceptor
import com.example.absensippkmb.Admin.utilities.AppExecutors
import com.example.lastprojectbangkit.data.UserPreference
import com.example.absensippkmb.Admin.data.network.ApiService
import com.example.absensippkmb.Admin.data.network.DataResponseKafas
import com.example.absensippkmb.Admin.database.UserScanDatabase
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository(
    private val pref: UserPreference,
    private val apiService: ApiService,
    private val userScanDatabase: UserScanDatabase,
    val appExecutors: AppExecutors
) {


    fun getUserToken() : LiveData<String> = pref.getUserToken().asLiveData()
    suspend fun saveUserToken(value: String) = pref.saveUserToken(value)

    fun getUserName() : LiveData<String> = pref.getUserName().asLiveData()
    suspend fun saveUserName(value: String) = pref.saveUserName(value)

    fun getUserEmail() : LiveData<String> = pref.getUserEmail().asLiveData()
    suspend fun saveUserEmail(value: String) = pref.saveUserEmail(value)

    fun getIsFirstTime() : LiveData<Boolean> = pref.isFirstTime().asLiveData()
    suspend fun saveIsFirstTime(value: Boolean) = pref.saveIsFirstTime(value)


    suspend fun clearCache() = pref.clearCache()

    private fun userStories(token: String): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor(token))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pkkmb-presensi.irvansn.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }


    fun userLogin(nim: String, password: String) : Call<DataResponseKafas>  {
        val user: Map<String, String> = mapOf(
            "nim" to nim,
            "password" to password
        )

        return apiService.kafasLogin(user)
    }

    fun userRegister(name: String, nim: String, password: String) : Call<DataResponseKafas>  {
        val user: Map<String, String> = mapOf(
            "name" to name,
            "nim" to nim,
            "password" to password
        )

        return apiService.kafasRegister(user)
    }

    fun uploadStory(
        photo: MultipartBody.Part,
        token: String,
        lat: Float? = null,
        lon: Float? = null): Call<DataResponseKafas> = userStories(token).postUserStory(photo, lat, lon)


   companion object {
        @Volatile
        private var instance: Repository? = null

        @JvmStatic
        fun getInstance(
            pref: UserPreference,
            apiService: ApiService,
            userScanDatabase: UserScanDatabase,
            appExecutors: AppExecutors,
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(pref,apiService,userScanDatabase,appExecutors)
            }.also { instance = it }
    }

}