package com.thrifthunter.activity.categoryJeans.paging

import com.thrifthunter.tools.ApiConfig
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductRepository

object JeansInjection {
    fun provideRepository(token: String) : JeansProductRepository {
        val apiService = ApiConfig().getApiService()
        return JeansProductRepository(apiService, token)
    }
}