package com.thrifthunter.activity.categoryHoodie.paging

import com.thrifthunter.tools.ApiConfig

object HoodieInjection {
    fun provideRepository(token: String) : HoodieProductRepository {
        val apiService = ApiConfig().getApiService()
        return HoodieProductRepository(apiService, token)
    }
}