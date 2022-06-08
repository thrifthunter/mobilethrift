package com.thrifthunter.activity.categoryJeans

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductRepository
import com.thrifthunter.auth.UserModel
import com.thrifthunter.tools.ListItem
import com.thrifthunter.tools.UserPreference

class JeansCategoryViewModel(private val mpreference: UserPreference, repo: ShoesProductRepository) :
    ViewModel() {
    fun getStories(): LiveData<UserModel> {
        return mpreference.getItems().asLiveData()
    }

    val item : LiveData<PagingData<ListItem>> = repo.getStories().cachedIn(viewModelScope)
}