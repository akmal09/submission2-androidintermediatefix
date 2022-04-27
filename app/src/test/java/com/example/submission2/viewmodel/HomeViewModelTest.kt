package com.example.submission2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.submission2.DataDummy
import com.example.submission2.MainCoroutineRule
import com.example.submission2.adapter.StoriesAdapter
import com.example.submission2.api.ListStory
import com.example.submission2.getOrAwaitValue
import com.example.submission2.model.LoginSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private val loginSessionTest:LoginSession = LoginSession(
        "user-SCXuMdF7ifeJEsLM",
            "cobaaja",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVNDWHVNZEY3aWZlSkVzTE0iLCJpYXQiOjE2NTA5MDQ2MzZ9.eO43m9ZdWw6Wt9u0IXuo_qJaAP96Dp8zDiAhJKXaNJY"
    )

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRules = MainCoroutineRule()
    @Mock
    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun getStories() = mainCoroutineRules.runBlockingTest {
        val dummyListStory = DataDummy.generateDummyListStory()
        val data = PagedTestDataSources.snapshot(dummyListStory)
        val listStory = MutableLiveData<PagingData<ListStory>>()
        listStory.value = data

        Mockito.`when`(homeViewModel.getStories(loginSessionTest)).thenReturn(listStory)
        val actualListStory = homeViewModel.getStories(loginSessionTest).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRules.dispatcher,
            workerDispatcher = mainCoroutineRules.dispatcher,
        )
        differ.submitData(actualListStory)

        advanceUntilIdle()
        Mockito.verify(homeViewModel).getStories(loginSessionTest)
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyListStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyListStory[0].name, differ.snapshot()[0]?.name)

    }

}

class PagedTestDataSources private constructor(private val items: List<ListStory>):
    PagingSource<Int, LiveData<List<ListStory>>>() {
    companion object{
        fun snapshot(items: List<ListStory>): PagingData<ListStory> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStory>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStory>>> {
        return LoadResult.Page(emptyList(), 0 , 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}