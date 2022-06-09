package com.thrifthunter.activity.categoryShirt.paging

import com.thrifthunter.tools.ApiConfig

object ShirtInjection {
    fun provideRepository(token: String) : ShirtProductRepository {
        val apiService = ApiConfig().getApiService()
        return ShirtProductRepository(apiService, token)
    }
}