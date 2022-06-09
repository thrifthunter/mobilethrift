package com.thrifthunter.activity.categorySweatShirt.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.thrifthunter.tools.ApiService
import com.thrifthunter.tools.ListItem

class SweatShirtProductRepository(private val apiService: ApiService, private val token: String) {

    fun getStories(): LiveData<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SweatShirtProductPagingSource(apiService,token)
            }
        ).liveData
    }
}