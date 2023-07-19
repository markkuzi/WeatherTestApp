package com.example.main.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.main.domain.MainWeatherUseCase
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var useCase: TestMainWeatherUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun init() {
        useCase = TestMainWeatherUseCase()
        viewModel = MainViewModel(useCase)
    }

    @Test
    fun `test init load weather with null`() {
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)

        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(useCase.getWeatherCalledList[0].value, viewModel.mainWeather.value)

        useCase.changeExpectedWeather(
            MainWeather(
                "today",
                "Moscow",
                "35",
                "40",
                "10",
                "10",
                1,
                "Rains"
            )
        )

        viewModel.loadWeather(null)

        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)

        assertEquals(
            MainWeather("today", "Moscow", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )

    }

    @Test
    fun `test success load weather`() {
        useCase.changeExpectedResult(ResponseResult.Success())

        useCase.changeSuccess(true)

        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(useCase.getWeatherCalledList[0].value, viewModel.mainWeather.value)

        useCase.changeExpectedWeather(
            MainWeather(
                "today",
                "Moscow",
                "35",
                "40",
                "10",
                "10",
                1,
                "Rains"
            )
        )
        viewModel.loadWeather("Moscow")

        assertEquals(1, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)

        assertEquals(
            MainWeather("today", "Moscow", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )

    }

    @Test
    fun `test failure load weather`() {

        useCase.changeExpectedResult(ResponseResult.Error("error message"))
        useCase.changeSuccess(false)

        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)

        viewModel.loadWeather("Moscow")

        assertEquals(1, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Error)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals("error message", viewModel.viewState.value?.message)
        assertEquals(1, useCase.getWeatherCalledList.size)

    }
}


private class TestMainWeatherUseCase : MainWeatherUseCase {

    private var result: ResponseResult = ResponseResult.Success()
    private var weather = MainWeather(
        "", "Saint Petersburg", "", "", "", "", 0, ""
    )
    private var weatherFlow = MutableStateFlow(
        MainWeather(
            "", "Saint Petersburg", "", "", "", "", 0, ""
        )
    )
    private var success: Boolean = true

    val loadCalledList = mutableListOf<ResponseResult>()
    val getWeatherCalledList = mutableListOf<StateFlow<MainWeather>>()
    val saveCityCalledList = mutableListOf<Boolean>()

    fun changeExpectedResult(newResult: ResponseResult) {
        result = newResult
    }

    fun changeExpectedWeather(newWeather: MainWeather) {
        weather = newWeather
    }

    fun changeSuccess(success: Boolean) {
        this.success = success
    }

    override suspend fun loadWeather(): ResponseResult {
        if (success) {
            weatherFlow.value = weather
        }
        loadCalledList.add(result)
        return result
    }

    override fun getMainWeather(): Flow<MainWeather> {
        getWeatherCalledList.add(weatherFlow)
        return weatherFlow.asStateFlow()
    }

    override suspend fun saveCityName(city: String) {
        saveCityCalledList.add(true)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}