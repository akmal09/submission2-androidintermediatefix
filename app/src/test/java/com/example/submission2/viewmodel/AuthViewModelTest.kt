package com.example.submission2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.submission2.DataDummy
import com.example.submission2.MainCoroutineRule
import com.example.submission2.api.LoginResponse
import com.example.submission2.api.RegisterResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRules = MainCoroutineRule()
    @Mock
    private lateinit var authViewModel: AuthViewModel

    @Test
    fun registerLauncher() {
        val nameRegis = "nameregis"
        val emailRegis = "nameregis@mail.com"
        val passwordRegis = "nameregis12345"

        val dummyRegisterResponse = DataDummy.generateDummyRegisterResponse()
        val registerResponse : LiveData<RegisterResponse?> = dummyRegisterResponse

        Mockito.`when`(authViewModel.registerLauncher(nameRegis, emailRegis, passwordRegis)).thenReturn(registerResponse)
        val actualRegisterResponse = authViewModel.registerLauncher(nameRegis, emailRegis, passwordRegis)

        Mockito.verify(authViewModel).registerLauncher(nameRegis, emailRegis, passwordRegis)
        Assert.assertEquals(dummyRegisterResponse, actualRegisterResponse)

    }

    @Test
    fun loginLauncher() {
        val emailExist = "cobaaja@mail.com"
        val password = "cobaaja12345"
        val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
        val loginResponse : LiveData<LoginResponse?> = dummyLoginResponse

        Mockito.`when`(authViewModel.loginLauncher(emailExist, password)).thenReturn(loginResponse)
        val actualLoginResponse = authViewModel.loginLauncher(emailExist, password)

        Mockito.verify(authViewModel).loginLauncher(emailExist,password)
        Assert.assertEquals(dummyLoginResponse, actualLoginResponse)
    }

}