package com.thrifthunter.activity.category.paging

import com.thrifthunter.ApiConfig

object TShirtInjection {
    fun provideRepository(token: String) : TShirtProductRepository{
        val apiService = ApiConfig().getApiService()
        return TShirtProductRepository(apiService, token)
    }
}