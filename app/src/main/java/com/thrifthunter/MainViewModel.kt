package com.thrifthunter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thrifthunter.settings.ListStory
import com.thrifthunter.tools.UserPreferences
import com.thrifthunter.auth.UserModel
import com.thrifthunter.paging.TheRepository
import kotlinx.coroutines.launch

class MainViewModel(private val mpreference: UserPreferences, theRepository: TheRepository) :
    ViewModel() {
    fun getStories(): LiveData<UserModel> {
        return mpreference.getStories().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            mpreference.logout()
        }
    }

    val story : LiveData<PagingData<ListStory>> = theRepository.getStories().cachedIn(viewModelScope)
}