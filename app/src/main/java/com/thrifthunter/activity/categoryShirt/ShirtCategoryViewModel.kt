package com.thrifthunter.activity.categoryShirt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thrifthunter.activity.categoryShirt.paging.ShirtProductRepository
import com.thrifthunter.tools.UserModel
import com.thrifthunter.tools.ListItem
import com.thrifthunter.tools.UserPreference

class ShirtCategoryViewModel(private val mpreference: UserPreference, repo: ShirtProductRepository) :
    ViewModel() {
    fun getStories(): LiveData<UserModel> {
        return mpreference.getItems().asLiveData()
    }

    val item : LiveData<PagingData<ListItem>> = repo.getStories().cachedIn(viewModelScope)
}