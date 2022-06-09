package com.thrifthunter.activity.categoryTshirt.paging

import com.thrifthunter.tools.ApiConfig
import com.thrifthunter.activity.categoryShoes.paging.ShoesProductRepository

object TShirtInjection {
    fun provideRepository(token: String) : TShirtProductRepository {
        val apiService = ApiConfig().getApiService()
        return TShirtProductRepository(apiService, token)
    }
}