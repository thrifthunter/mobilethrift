package com.thrifthunter.activity.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thrifthunter.activity.category.paging.TShirtProductRepository
import com.thrifthunter.auth.UserModel
import com.thrifthunter.tools.ListItem
import com.thrifthunter.tools.UserPreference

class TShirtCategoryViewModel(private val mpreference: UserPreference, repo: TShirtProductRepository) :
    ViewModel() {
    fun getStories(): LiveData<UserModel> {
        return mpreference.getItems().asLiveData()
    }

    val item : LiveData<PagingData<ListItem>> = repo.getStories().cachedIn(viewModelScope)
}