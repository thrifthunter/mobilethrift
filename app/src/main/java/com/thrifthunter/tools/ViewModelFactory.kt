package com.thrifthunter.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thrifthunter.activity.categoryHoodie.HoodieCategoryViewModel
import com.thrifthunter.activity.categoryHoodie.paging.HoodieInjection
import com.thrifthunter.activity.categoryLongSleeve.LongSleeveCategoryViewModel
import com.thrifthunter.activity.categoryLongSleeve.paging.LongSleeveInjection
import com.thrifthunter.activity.categoryShirt.ShirtCategoryViewModel
import com.thrifthunter.activity.categoryShirt.paging.ShirtInjection
import com.thrifthunter.activity.categorySweatShirt.SweatShirtCategoryViewModel
import com.thrifthunter.activity.categorySweatShirt.paging.SweatShirtInjection
import com.thrifthunter.activity.main.MainViewModel
import com.thrifthunter.activity.profile.ProfileViewModel
import com.thrifthunter.activity.splash.SplashViewModel
import com.thrifthunter.auth.LoginViewModel
import com.thrifthunter.auth.RegistrationViewModel
import com.thrifthunter.paging.Injection

class ViewModelFactory(private val mpreference: UserPreference, private val token: String) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mpreference, Injection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(SweatShirtCategoryViewModel::class.java) -> {
                SweatShirtCategoryViewModel(mpreference, SweatShirtInjection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(LongSleeveCategoryViewModel::class.java) -> {
                LongSleeveCategoryViewModel(mpreference, LongSleeveInjection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(ShirtCategoryViewModel::class.java) -> {
                ShirtCategoryViewModel(mpreference, ShirtInjection.provideRepository(token)) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(mpreference) as T
            }
            modelClass.isAssignableFrom(HoodieCategoryViewModel::class.java) -> {
                HoodieCategoryViewModel(mpreference, HoodieInjection.provideRepository(token)) as T
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