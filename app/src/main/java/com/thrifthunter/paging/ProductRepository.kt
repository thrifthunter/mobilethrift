package com.thrifthunter.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.thrifthunter.settings.ApiService
import com.thrifthunter.settings.ListItem

class TheRepository(private val apiService: ApiService, private val token: String) {

    fun getStories(): LiveData<PagingData<ListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                ThePagingSource(apiService,token)
            }
        ).liveData
    }
}