package com.thrifthunter.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thrifthunter.tools.UserPreferences
import kotlinx.coroutines.launch

class LoginViewModel(private val mpreference: UserPreferences) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return mpreference.getStories().asLiveData()
    }

    fun login(token: String) {
        viewModelScope.launch {
            mpreference.login(token)
        }
    }
}