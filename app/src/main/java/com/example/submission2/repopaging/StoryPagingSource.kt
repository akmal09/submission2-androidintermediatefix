package com.example.submission2.repopaging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submission2.api.ApiService
import com.example.submission2.api.ListStory
import com.example.submission2.api.StoriesResponse
import com.example.submission2.model.LoginSession
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class StoryPagingSource(private val apiService: ApiService, loginSession: LoginSession) : PagingSource<Int, ListStory>() {

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    private var loginSessionLocal : LoginSession = loginSession
    private var listStory = ArrayList<ListStory>()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories("Bearer ${loginSessionLocal.token}",page,params.loadSize)

            Log.d(".StoryPagingResource","data paging : ${responseData.listStory}")
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else page + 1
            )
        }catch (exception: IOException) {
            Log.e("erorr io", "load: 11111111111111111 " + exception )
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("error http", "load: 11111111111111111 " + exception )
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}