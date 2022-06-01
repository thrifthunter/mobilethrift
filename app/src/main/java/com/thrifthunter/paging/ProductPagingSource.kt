package com.thrifthunter.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thrifthunter.settings.ApiService
import com.thrifthunter.settings.ListItem

class ThePagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, ListItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(position, params.loadSize, "Bearer $token").listItem


            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}