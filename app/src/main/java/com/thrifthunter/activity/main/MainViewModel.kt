package com.thrifthunter.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thrifthunter.tools.UserModel
import com.thrifthunter.tools.UserPreference
import com.thrifthunter.paging.ProductRepository
import com.thrifthunter.tools.ListItem
import kotlinx.coroutines.launch

class MainViewModel(private val mpreference: UserPreference, productRepository: ProductRepository) :
    ViewModel() {
    fun getStories(): LiveData<UserModel> {
        return mpreference.getItems().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            mpreference.logout()
        }
    }

    val item : LiveData<PagingData<ListItem>> = productRepository.getStories().cachedIn(viewModelScope)
}