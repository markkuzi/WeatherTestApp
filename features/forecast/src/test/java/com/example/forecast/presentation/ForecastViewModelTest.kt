package com.example.forecast.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.forecast.domain.ForecastWeatherUseCase
import com.example.forecast.domain.entity.ForecastWeather
import com.example.forecast.domain.entity.WeatherList
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
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ForecastViewModelTest {

    private lateinit var viewModel: ForecastViewModel
    private lateinit var useCase: TestForecastWeatherUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test success init load weather`() {
        //prepare data
        useCase = TestForecastWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg", emptyList())
        )

        //init view model
        viewModel = ForecastViewModel(useCase)

        //check after init
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(1, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(true, useCase.getCacheCalledList[0])
        assertEquals(
            ForecastWeather("Saint Petersburg", emptyList()),
            viewModel.forecastWeather.value
        )
    }

    @Test
    fun `test failure init load weather`() {
        //prepare data
        useCase = TestForecastWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Error("Error message"))
        useCase.changeSuccess(false)
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg", emptyList())
        )

        //init view model
        viewModel = ForecastViewModel(useCase)

        //check after init
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(1, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals(true, useCase.getCacheCalledList[0])
        assertEquals("Error message", viewModel.viewState.value?.message)
    }

    @Test
    fun `test success refresh weather after success init`() {
        //prepare data
        useCase = TestForecastWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg", emptyList())
        )

        //init view model
        viewModel = ForecastViewModel(useCase)

        //check after init
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(1, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(true, useCase.getCacheCalledList[0])
        assertEquals(
            ForecastWeather("Saint Petersburg", emptyList()),
            viewModel.forecastWeather.value
        )

        //change data
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg",
                listOf(WeatherList("35", "35", "35", "35", 1))
            )
        )

        //call refresh weather
        viewModel.refreshWeather()

        //check after refresh weather
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(2, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(false, useCase.getCacheCalledList[1])
        assertEquals(
            ForecastWeather("Saint Petersburg",
                listOf(WeatherList("35", "35", "35", "35", 1))
            ),
            viewModel.forecastWeather.value
        )
    }

    @Test
    fun `test failure refresh weather after success init`() {
        //prepare data
        useCase = TestForecastWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg", emptyList())
        )

        //init view model
        viewModel = ForecastViewModel(useCase)

        //check after init
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(1, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(true, useCase.getCacheCalledList[0])
        assertEquals(
            ForecastWeather("Saint Petersburg", emptyList()),
            viewModel.forecastWeather.value
        )

        //change data
        useCase.changeExpectedResult(ResponseResult.Error("Error message"))
        useCase.changeSuccess(false)

        //call refresh weather
        viewModel.refreshWeather()

        //check after refresh weather
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(2, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals(false, useCase.getCacheCalledList[1])
        assertEquals("Error message", viewModel.viewState.value?.message)
    }

    @Test
    fun `test success refresh weather after failure init`() {
        //prepare data
        useCase = TestForecastWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Error("Error message"))
        useCase.changeSuccess(false)
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg", emptyList())
        )

        //init view model
        viewModel = ForecastViewModel(useCase)

        //check after init
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(1, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals(true, useCase.getCacheCalledList[0])
        assertEquals("Error message", viewModel.viewState.value?.message)

        //change data
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg",
                listOf(WeatherList("35", "35", "35", "35", 1))
            )
        )

        //call refresh weather
        viewModel.refreshWeather()

        //check after refresh weather
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(2, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(false, useCase.getCacheCalledList[1])
        assertEquals(
            ForecastWeather("Saint Petersburg",
                listOf(WeatherList("35", "35", "35", "35", 1))
            ),
            viewModel.forecastWeather.value
        )
    }

    @Test
    fun `test failure refresh weather after failure init`() {
        //prepare data
        useCase = TestForecastWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Error("Error message"))
        useCase.changeSuccess(false)
        useCase.changeExpectedWeather(
            ForecastWeather("Saint Petersburg", emptyList())
        )

        //init view model
        viewModel = ForecastViewModel(useCase)

        //check after init
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(1, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals(true, useCase.getCacheCalledList[0])
        assertEquals("Error message", viewModel.viewState.value?.message)

        //call refresh weather
        viewModel.refreshWeather()

        //check after refresh weather
        assertEquals(2, useCase.loadCalledList.size)
        assertEquals(1, useCase.getForecastWeatherCalledList.size)
        assertEquals(2, useCase.getCacheCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals(false, useCase.getCacheCalledList[1])
        assertEquals("Error message", viewModel.viewState.value?.message)
    }

}

class TestForecastWeatherUseCase : ForecastWeatherUseCase {

    private var result: ResponseResult = ResponseResult.Success()
    private var weather = ForecastWeather(
        "Saint Petersburg",
        emptyList()
    )
    private var forecastWeatherFlow = MutableStateFlow(weather)
    private var success: Boolean = true

    val loadCalledList = mutableListOf<ResponseResult>()
    val getForecastWeatherCalledList = mutableListOf<StateFlow<ForecastWeather>>()
    val getCacheCalledList = mutableListOf<Boolean>()

    fun changeExpectedResult(newResult: ResponseResult) {
        result = newResult
    }

    fun changeExpectedWeather(newWeather: ForecastWeather) {
        weather = newWeather
    }

    fun changeSuccess(success: Boolean) {
        this.success = success
    }

    fun changeFlowValue() {
        forecastWeatherFlow.value = weather
    }

    override suspend fun loadForecastWeather(getCache: Boolean): ResponseResult {
        if (success) {
            changeFlowValue()
        }
        loadCalledList.add(result)
        getCacheCalledList.add(getCache)
        return result
    }

    override fun getForecastWeather(): Flow<ForecastWeather> {
        getForecastWeatherCalledList.add(forecastWeatherFlow)
        return forecastWeatherFlow.asStateFlow()
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