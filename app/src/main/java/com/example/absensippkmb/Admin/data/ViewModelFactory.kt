package com.example.absensippkmb.Admin.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.absensippkmb.Admin.Dashboard.AuthenticationViewModel
import com.example.absensippkmb.Admin.Dashboard.ViewModelDashboardUtama
import com.example.lastprojectbangkit.data.UserPreference
import com.example.absensippkmb.Admin.data.network.ApiConfig
import com.example.absensippkmb.Admin.data.setting.SettingViewModel

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = "settings"
)


class ViewModelFactory private constructor(private val userRepository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) -> AuthenticationViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(ViewModelDashboardUtama::class.java) -> ViewModelDashboardUtama(userRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")

        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        private var INSTANCE: UserPreference? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    ApiConfig.provideUserRepository(context)
                )
            }.also { instance = it }
    }
}