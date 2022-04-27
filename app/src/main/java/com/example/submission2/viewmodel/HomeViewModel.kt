package com.example.submission2.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.example.submission2.api.ApiConfig
import com.example.submission2.api.ListStory
import com.example.submission2.api.StoriesResponse
import com.example.submission2.injection.Injection
import com.example.submission2.model.LoginSession
import com.example.submission2.repopaging.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val storyRepository: StoryRepository): ViewModel() {

    fun getStories(loginSession: LoginSession): LiveData<PagingData<ListStory>> {
        Log.d("lihat data", "${loginSession.token}\n${storyRepository.getUser(loginSession)}")
         return storyRepository.getUser(loginSession)
    }
}

