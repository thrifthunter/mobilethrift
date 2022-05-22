package com.thrifthunter.paging

import com.thrifthunter.ApiConfig

object Injection {
    fun provideRepository(token: String) : TheRepository{
        val apiService = ApiConfig().getApiService()
        return TheRepository(apiService, token)
    }
}