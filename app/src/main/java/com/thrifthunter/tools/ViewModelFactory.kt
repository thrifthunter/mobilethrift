package com.thrifthunter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thrifthunter.activity.categoryJacket.JacketCategoryViewModel
import com.thrifthunter.activity.categoryJacket.paging.JacketInjection
import com.thrifthunter.activity.categoryJeans.JeansCategoryViewModel
import com.thrifthunter.activity.categoryJeans.paging.JeansInjection
import com.thrifthunter.activity.categoryShoes.ShoesCategoryViewModel
import com.thrifthunter.activity.categoryShoes.paging.ShoesInjection
import com.thrifthunter.activity.categoryTshirt.TShirtCategoryViewModel
import com.thrifthunter.activity.categoryTshirt.paging.TShirtInjection
import com.thrifthunter.auth.LoginViewModel
import com.thrifthunter.auth.RegistrationViewModel
import com.thrifthunter.activity.main.MainViewModel
import com.thrifthunter.activity.profile.ProfileViewModel
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
            modelClass.isAssignableFrom(ShoesCategoryViewModel::class.java) -> {
                ShoesCategoryViewModel(mpreference, ShoesInjection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(JeansCategoryViewModel::class.java) -> {
                JeansCategoryViewModel(mpreference, JeansInjection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(JacketCategoryViewModel::class.java) -> {
                JacketCategoryViewModel(mpreference, JacketInjection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(mpreference) as T
            }
            modelClass.isAssignableFrom(TShirtCategoryViewModel::class.java) -> {
                TShirtCategoryViewModel(mpreference, TShirtInjection.provideRepository(token)) as T
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