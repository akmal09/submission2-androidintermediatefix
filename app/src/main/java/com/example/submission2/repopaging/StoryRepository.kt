package com.example.submission2.repopaging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submission2.api.*
import com.example.submission2.model.LoginSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(private val apiService: ApiService) {

    val loginResponse = MutableLiveData<LoginResponse?>()
    val registerResponse = MutableLiveData<RegisterResponse?>()
    val mapsListStory = MutableLiveData<List<ListStory>>()

    fun getUser(loginSession: LoginSession): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService,loginSession)
            }
        ).liveData
    }

    fun registerLauncherRepo(name:String, email: String, password:String) {
        val launchRegister = ApiConfig.getApiService().postRegister(name, email, password)
        launchRegister.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        Log.d(".RegisterActivity","regis berhasil $responseBody")
                        registerResponse.postValue(responseBody)
                    }
                }else{
                    val errMess = when (response.code()) {
                        401 -> "${response.code()} : Bad Request"
                        403 -> "${response.code()} : Forbidden"
                        404 -> "${response.code()} : Not Found"
                        else -> "${response.code()} : $response"
                    }
                    Log.e(".RegisterActivity","$errMess")
                    registerResponse.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(".RegisterActivity","retrofit register failure")
            }
        })
    }

    fun loginLauncherRepo(email: String, password:String){
        val launchLogin = ApiConfig.getApiService().login(email, password)
        launchLogin.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        Log.d(".LoginActivity","login berhasil $responseBody")
                        loginResponse.postValue(responseBody)
                    }
                }else{
                    Log.e(".LoginActivity","login gagal ${response.message()}")
                    loginResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(".RegisterActivity","retrofit login failure")
            }

        })
    }

    fun setMapsListStory(loginSession: LoginSession) {
        val storiesMaps = ApiConfig.getApiService().getStoriesMaps("Bearer ${loginSession.token}",null,null)
        storiesMaps.enqueue(object : Callback<StoriesResponse>{
            override fun onResponse(call: Call<StoriesResponse>, response: Response<StoriesResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        mapsListStory.postValue(responseBody.listStory)
                    }
                }else{
                    Log.e(".LoginActivity","login gagal ${response.message()}")
                    mapsListStory.postValue(response.body()!!.listStory)
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e(".MapsActivity","retrofit failure")
            }
        })
    }

    fun getLoginResponse(): LiveData<LoginResponse?> {
        return loginResponse
    }

    fun getRegisterResponse(): LiveData<RegisterResponse?> {
        return registerResponse
    }

    fun getMapsListStory(): LiveData<List<ListStory>> {
        return mapsListStory
    }

    companion object{
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstace(
            apiService: ApiService
        ): StoryRepository = instance ?: synchronized(this){
            instance ?: StoryRepository(apiService)
        }.also { instance= it }
    }
}