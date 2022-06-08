package com.thrifthunter.activity.categoryTshirt.paging

import com.thrifthunter.ApiConfig
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductRepository

object TShirtInjection {
    fun provideRepository(token: String) : ShoesProductRepository {
        val apiService = ApiConfig().getApiService()
        return ShoesProductRepository(apiService, token)
    }
}