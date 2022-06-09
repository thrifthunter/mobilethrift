package com.thrifthunter.activity.categoryJacket

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thrifthunter.activity.categoryJacket.paging.JacketProductRepository
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductRepository
import com.thrifthunter.auth.UserModel
import com.thrifthunter.tools.ListItem
import com.thrifthunter.tools.UserPreference

class JacketCategoryViewModel(private val mpreference: UserPreference, repo: JacketProductRepository) :
    ViewModel() {
    fun getStories(): LiveData<UserModel> {
        return mpreference.getItems().asLiveData()
    }

    val item : LiveData<PagingData<ListItem>> = repo.getStories().cachedIn(viewModelScope)
}