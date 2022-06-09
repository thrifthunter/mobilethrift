package com.thrifthunter.activity.categoryJacket.paging

import com.thrifthunter.tools.ApiConfig

object JacketInjection {
    fun provideRepository(token: String) : JacketProductRepository {
        val apiService = ApiConfig().getApiService()
        return JacketProductRepository(apiService, token)
    }
}