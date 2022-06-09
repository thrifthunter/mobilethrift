package com.thrifthunter.activity.categoryLongSleeve.paging

import com.thrifthunter.tools.ApiConfig

object LongSleeveInjection {
    fun provideRepository(token: String) : LongSleeveProductRepository {
        val apiService = ApiConfig().getApiService()
        return LongSleeveProductRepository(apiService, token)
    }
}