package com.thrifthunter.activity.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thrifthunter.tools.UserModel
import com.thrifthunter.tools.UserPreference

class SplashViewModel(private val userPreference: UserPreference) :
    ViewModel() {
    fun getItems(): LiveData<UserModel> {
        return userPreference.getItems().asLiveData()
    }
}