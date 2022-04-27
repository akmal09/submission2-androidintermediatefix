package com.example.submission2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.submission2.api.ListStory
import com.example.submission2.api.LoginResponse
import com.example.submission2.api.LoginResult
import com.example.submission2.api.RegisterResponse

object DataDummy {
    fun generateDummyListStory(): List<ListStory> {
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val listStory = ListStory(
                "url $i",
                "dateFormat",
                "name $i",
                "description $i",
                i.toDouble(),
                i.toString(),
                i.toDouble(),
            )
            items.add(listStory)
        }
        return items
    }

    fun generateDummyRegisterResponse():LiveData<RegisterResponse?> = liveData {
        val registerResponse = RegisterResponse(
            false,
            "Message dummy"
        )
        registerResponse
    }

    fun generateDummyLoginResponse():LiveData<LoginResponse?> = liveData {
        val loginResponse = LoginResponse(
            false,
            "Message dummy",
            LoginResult(
                "user-id",
                "username",
                "user-token"
            )
        )
        loginResponse
    }

    fun generateDummyMapsListStory():LiveData<List<ListStory>> = liveData {
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val listStory = ListStory(
                "url $i",
                "dateFormat",
                "name $i",
                "description $i",
                i.toDouble(),
                i.toString(),
                i.toDouble(),
            )
            items.add(listStory)
        }
        items
    }
}