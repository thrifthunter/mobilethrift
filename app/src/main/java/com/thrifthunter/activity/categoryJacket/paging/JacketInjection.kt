package com.thrifthunter.activity.categoryJacket.paging

import com.thrifthunter.ApiConfig
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductRepository

object JacketInjection {
    fun provideRepository(token: String) : ShoesProductRepository {
        val apiService = ApiConfig().getApiService()
        return ShoesProductRepository(apiService, token)
    }
}