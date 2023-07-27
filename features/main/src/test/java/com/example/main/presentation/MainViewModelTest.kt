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

    @Test
    fun `test init load weather with null`() {
        //Prepare data
        useCase = TestMainWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
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
        //init view model
        viewModel = MainViewModel(useCase)

        //check view model init
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            MainWeather("today", "Moscow", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )

        //call load weather
        viewModel.loadWeather(null)


        //check load weather
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            MainWeather("today", "Moscow", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )
    }

    @Test
    fun `test success load weather when was success`() {

        //Prepare data
        useCase = TestMainWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            MainWeather(
                "today",
                "Saint Petersburg",
                "35",
                "40",
                "10",
                "10",
                1,
                "Rains"
            )
        )

        //init view model
        viewModel = MainViewModel(useCase)

        //check view model init
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            MainWeather("today", "Saint Petersburg", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )

        //change data
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

        //call load weather
        viewModel.loadWeather("Moscow")

        //check load weather
        assertEquals(1, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals("Moscow", useCase.saveCityCalledList[0])
        assertEquals(
            MainWeather("today", "Moscow", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )
    }

    @Test
    fun `test success load weather when was failure`() {

        //Prepare data
        useCase = TestMainWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Error("Error message"))
        useCase.changeSuccess(false)

        //init view model
        viewModel = MainViewModel(useCase)

        //check view model init
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Error)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)

        //change data
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
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)

        //call load weather
        viewModel.loadWeather("Moscow")

        //check load weather
        assertEquals(1, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals("Moscow", useCase.saveCityCalledList[0])
        assertEquals(
            MainWeather("today", "Moscow", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )
    }

    @Test
    fun `test failure load weather when was success`() {

        //Prepare data
        useCase = TestMainWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)

        //init view model
        viewModel = MainViewModel(useCase)

        //check view model init
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)

        //change data
        useCase.changeExpectedResult(ResponseResult.Error("error message"))
        useCase.changeSuccess(false)

        //call load weather
        viewModel.loadWeather("Moscow")

        //check load weather
        assertEquals(1, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Error)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals("Moscow", useCase.saveCityCalledList[0])
        assertEquals("error message", viewModel.viewState.value?.message)
    }

    @Test
    fun `test failure load weather when was failure`() {

        //Prepare data
        useCase = TestMainWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Error("error message"))
        useCase.changeSuccess(false)

        //init view model
        viewModel = MainViewModel(useCase)

        //check view model init
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Error)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)

        //call load weather
        viewModel.loadWeather("Moscow")

        //check load weather
        assertEquals(1, useCase.saveCityCalledList.size)
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[1] is ResponseResult.Error)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals("error message", viewModel.viewState.value?.message)
        assertEquals("Moscow", useCase.saveCityCalledList[0])
        assertEquals(1, useCase.getWeatherCalledList.size)
    }

    @Test
    fun `test update flow weather`() {

        //Prepare data
        useCase = TestMainWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            MainWeather(
                "today",
                "Saint Petersburg",
                "35",
                "10",
                "40",
                "10",
                1,
                "Rains"
            )
        )

        //init view model
        viewModel = MainViewModel(useCase)

        //check view model init
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            MainWeather("today", "Saint Petersburg", "35", "10", "40", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )

        //change data
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
        useCase.changeFlowValue()

        //check after change flow
        assertEquals(0, useCase.saveCityCalledList.size)
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            MainWeather("today", "Moscow", "35", "40", "10", "10", 1, "Rains"),
            viewModel.mainWeather.value
        )
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
    val saveCityCalledList = mutableListOf<String>()

    fun changeExpectedResult(newResult: ResponseResult) {
        result = newResult
    }

    fun changeExpectedWeather(newWeather: MainWeather) {
        weather = newWeather
    }

    fun changeSuccess(success: Boolean) {
        this.success = success
    }

    fun changeFlowValue() {
        weatherFlow.value = weather
    }

    override suspend fun loadWeather(): ResponseResult {
        if (success) {
            changeFlowValue()
        }
        loadCalledList.add(result)
        return result
    }

    override fun getMainWeather(): Flow<MainWeather> {
        getWeatherCalledList.add(weatherFlow)
        return weatherFlow.asStateFlow()
    }

    override suspend fun saveCityName(city: String) {
        saveCityCalledList.add(city)
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