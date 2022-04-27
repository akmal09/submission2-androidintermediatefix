package com.example.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.api.ApiConfig
import com.example.submission2.api.LoginResponse
import com.example.submission2.api.LoginResult
import com.example.submission2.api.RegisterResponse
import com.example.submission2.repopaging.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private val storyRepository: StoryRepository): ViewModel() {

    fun registerLauncher(name:String, email: String, password:String):LiveData<RegisterResponse?> {
        storyRepository.registerLauncherRepo(name, email, password)
        return storyRepository.getRegisterResponse()
    }

    fun loginLauncher(email: String, password:String) :LiveData<LoginResponse?>{
        storyRepository.loginLauncherRepo(email,password)
        return storyRepository.getLoginResponse()
    }

}