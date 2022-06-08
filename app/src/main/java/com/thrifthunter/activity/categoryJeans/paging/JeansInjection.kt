package com.thrifthunter.activity.categoryJeans.paging

import com.thrifthunter.ApiConfig
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductRepository

object JeansInjection {
    fun provideRepository(token: String) : ShoesProductRepository {
        val apiService = ApiConfig().getApiService()
        return ShoesProductRepository(apiService, token)
    }
}