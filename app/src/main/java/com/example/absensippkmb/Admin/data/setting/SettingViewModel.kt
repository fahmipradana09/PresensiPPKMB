package com.example.absensippkmb.Admin.data.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.absensippkmb.Admin.data.Repository


class SettingViewModel(private val userRepository : Repository) : ViewModel() {
    fun getUserName() : LiveData<String> = userRepository.getUserName()
    fun getUserEmail() : LiveData<String> = userRepository.getUserEmail()


}