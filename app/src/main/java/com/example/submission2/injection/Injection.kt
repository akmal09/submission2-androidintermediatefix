package com.example.submission2.injection

import android.content.Context
import com.example.submission2.api.ApiConfig
import com.example.submission2.repopaging.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstace(apiService)
    }
}