package com.thrifthunter.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thrifthunter.tools.UserPreferences
import kotlinx.coroutines.launch

class RegistrationViewModel(private val mpreference: UserPreferences) : ViewModel() {

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            mpreference.saveUser(user)
        }
    }
}