package com.example.submission2.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission2.api.ApiConfig
import com.example.submission2.api.ListStory
import com.example.submission2.api.StoriesResponse
import com.example.submission2.injection.Injection
import com.example.submission2.model.LoginSession
import com.example.submission2.repopaging.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getMapsListStory(loginSession: LoginSession): LiveData<List<ListStory>>{
        storyRepository.setMapsListStory(loginSession)
        return storyRepository.getMapsListStory()
    }
}
