package com.thrifthunter.paging

import com.thrifthunter.tools.ApiConfig

object Injection {
    fun provideRepository(token: String) : ProductRepository{
        val apiService = ApiConfig().getApiService()
        return ProductRepository(apiService, token)
    }
}