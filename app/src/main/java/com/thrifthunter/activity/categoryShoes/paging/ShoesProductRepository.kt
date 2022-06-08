package com.thrifthunter.activity.categoryShoes.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductPagingSource
import com.thrifthunter.tools.ApiService
import com.thrifthunter.tools.ListItem

class ShoesProductRepository(private val apiService: ApiService, private val token: String) {

    fun getStories(): LiveData<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                ShoesProductPagingSource(apiService,token)
            }
        ).liveData
    }
}