package com.example.submission2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.submission2.DataDummy
import com.example.submission2.MainCoroutineRule
import com.example.submission2.api.ListStory
import com.example.submission2.model.LoginSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRules = MainCoroutineRule()
    @Mock
    private lateinit var mapsViewModel: MapsViewModel


    @Test
    fun getMapsListStory() {
        val loginSessionMapsTest: LoginSession = LoginSession(
            "user-SCXuMdF7ifeJEsLM",
            "cobaaja",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVNDWHVNZEY3aWZlSkVzTE0iLCJpYXQiOjE2NTA5MDQ2MzZ9.eO43m9ZdWw6Wt9u0IXuo_qJaAP96Dp8zDiAhJKXaNJY"
        )
        val dummyMapsListStory = DataDummy.generateDummyMapsListStory()
        val mapsListStory : LiveData<List<ListStory>> = dummyMapsListStory

        Mockito.`when`(mapsViewModel.getMapsListStory(loginSessionMapsTest)).thenReturn(mapsListStory)
        val actualMapsListStory = mapsViewModel.getMapsListStory(loginSessionMapsTest)

        Mockito.verify(mapsViewModel).getMapsListStory(loginSessionMapsTest)
        Assert.assertEquals(dummyMapsListStory, actualMapsListStory)
//        Assert.assertEquals(dummyMapsListStory.size, listStory)
//        Assert.assertEquals(dummyMapsListStory[0].name, differ.snapshot()[0]?.name)

    }
}