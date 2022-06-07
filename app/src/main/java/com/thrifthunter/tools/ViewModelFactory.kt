package com.thrifthunter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thrifthunter.auth.LoginViewModel
import com.thrifthunter.auth.RegistrationViewModel
import com.thrifthunter.activity.main.MainViewModel
import com.thrifthunter.tools.UserPreference
import com.thrifthunter.paging.Injection
import com.thrifthunter.activity.splash.SplashViewModel

class ViewModelFactory(private val mpreference: UserPreference, private val token: String) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mpreference, Injection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(RegistrationViewModel::class.java) -> {
                RegistrationViewModel(mpreference) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(mpreference) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(mpreference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}