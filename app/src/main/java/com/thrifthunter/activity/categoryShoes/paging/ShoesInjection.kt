package com.thrifthunter.activity.categoryShoes.paging

import com.thrifthunter.tools.ApiConfig

object ShoesInjection {
    fun provideRepository(token: String) : ShoesProductRepository {
        val apiService = ApiConfig().getApiService()
        return ShoesProductRepository(apiService, token)
    }
}