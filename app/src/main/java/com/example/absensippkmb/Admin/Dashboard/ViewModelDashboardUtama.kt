package com.example.absensippkmb.Admin.Dashboard

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.absensippkmb.Admin.data.Repository
import com.example.absensippkmb.Admin.data.network.DataResponseKafas
import com.example.absensippkmb.Admin.utilities.Event
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelDashboardUtama (private val userRepository : Repository): ViewModel(){
    private var _error = MutableLiveData<Event<Boolean>>()
    val error: LiveData<Event<Boolean>> = _error

    private var _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private var _loading = MutableLiveData<Event<Boolean>>()
    val loading: LiveData<Event<Boolean>> = _loading

    var myLocation = MutableLiveData<Location?>()

    fun uploadStory(photo: MultipartBody.Part, token: String, lat: Float? = null, lon: Float? = null) {
        _loading.value = Event(true)
        val client = userRepository.uploadStory(photo, token, lat, lon)
        client.enqueue(object: Callback<DataResponseKafas> {
            override fun onResponse(call: Call<DataResponseKafas>, response: Response<DataResponseKafas>) {
                userRepository.appExecutors.networkIO.execute {
                    if (response.isSuccessful) {
                        _error.postValue(Event(false))
                        _loading.postValue(Event(false))
                    } else {
                        _error.postValue(Event(true))
                        _message.postValue(Event(response.message()))
                        _loading.postValue(Event(false))
                    }
                }
            }

            override fun onFailure(call: Call<DataResponseKafas>, t: Throwable) {
                _error.value = Event(true)
                _message.value = Event(t.message.toString())
            }
        })
    }

}