package com.thrifthunter.activity.categoryLongSleeve.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.thrifthunter.activity.categoryShirt.paging.LongSleeveProductPagingSource
import com.thrifthunter.tools.ApiService
import com.thrifthunter.tools.ListItem

class LongSleeveProductRepository(private val apiService: ApiService, private val token: String) {

    fun getStories(): LiveData<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                LongSleeveProductPagingSource(apiService,token)
            }
        ).liveData
    }
}