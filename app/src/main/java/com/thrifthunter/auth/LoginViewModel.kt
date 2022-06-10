package com.thrifthunter.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thrifthunter.tools.UserModel
import com.thrifthunter.tools.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val mpreference: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return mpreference.getItems().asLiveData()
    }

    fun login(token: String) {
        viewModelScope.launch {
            mpreference.login(token)
        }
    }
}