package com.thrifthunter.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegistrationViewModel(private val mpreference: UserPreference) : ViewModel() {

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            mpreference.saveUser(user)
        }
    }
}