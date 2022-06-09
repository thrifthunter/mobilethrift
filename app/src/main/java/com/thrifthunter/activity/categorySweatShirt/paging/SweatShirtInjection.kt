package com.thrifthunter.activity.categorySweatShirt.paging

import com.thrifthunter.tools.ApiConfig

object SweatShirtInjection {
    fun provideRepository(token: String) : SweatShirtProductRepository {
        val apiService = ApiConfig().getApiService()
        return SweatShirtProductRepository(apiService, token)
    }
}