package com.thrifthunter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thrifthunter.auth.UserModel
import com.thrifthunter.tools.UserPreference
import com.thrifthunter.paging.TheRepository
import com.thrifthunter.settings.ListItem
import kotlinx.coroutines.launch

class MainViewModel(private val mpreference: UserPreference, theRepository: TheRepository) :
    ViewModel() {
    fun getStories(): LiveData<UserModel> {
        return mpreference.getItems().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            mpreference.logout()
        }
    }

    val item : LiveData<PagingData<ListItem>> = theRepository.getStories().cachedIn(viewModelScope)
}