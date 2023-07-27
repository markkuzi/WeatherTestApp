package com.example.details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.details.domain.DetailsWeatherUseCase
import com.example.details.domain.entity.DetailsWeather
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

class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var useCase: TestDetailsWeatherUseCase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test success load weather`() {
        //prepare data
        useCase = TestDetailsWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            DetailsWeather(
                "35",
                "Saint Petersburg",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                1,
            )
        )
        useCase.changeFlowValue()

        //init view model
        viewModel = DetailsViewModel(useCase)

        //check after init
        assertEquals(0, useCase.loadCalledList.size)
        assertEquals(1, useCase.getDetailsWeatherCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            DetailsWeather(
                "35", "Saint Petersburg", "35", "35", "35",
                "35", "35", "35", "35", "35", "35",
                "35", "35", "35", "35", 1
            ),
            viewModel.detailsWeather.value
        )

        //call load weather
        viewModel.loadWeather()

        //check load weather
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(1, useCase.getDetailsWeatherCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Success)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            DetailsWeather(
                "35", "Saint Petersburg", "35", "35", "35",
                "35", "35", "35", "35", "35", "35",
                "35", "35", "35", "35", 1
            ),
            viewModel.detailsWeather.value
        )
    }

    @Test
    fun `test failure load weather`() {

        //prepare data
        useCase = TestDetailsWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Error("ErrorMessage"))
        useCase.changeSuccess(false)
        useCase.changeExpectedWeather(
            DetailsWeather(
                "35",
                "Saint Petersburg",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                1,
            )
        )
        useCase.changeFlowValue()

        //init view model
        viewModel = DetailsViewModel(useCase)

        //check after init
        assertEquals(0, useCase.loadCalledList.size)
        assertEquals(1, useCase.getDetailsWeatherCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            DetailsWeather(
                "35", "Saint Petersburg", "35", "35", "35",
                "35", "35", "35", "35", "35", "35",
                "35", "35", "35", "35", 1
            ),
            viewModel.detailsWeather.value
        )

        //call load weather
        viewModel.loadWeather()

        //check load weather
        assertEquals(1, useCase.loadCalledList.size)
        assertEquals(true, useCase.loadCalledList[0] is ResponseResult.Error)
        assertEquals(true, viewModel.viewState.value is ViewState.Error)
        assertEquals("ErrorMessage", viewModel.viewState.value?.message)

    }

    @Test
    fun `test update flow weather`() {

        //prepare data
        useCase = TestDetailsWeatherUseCase()
        useCase.changeExpectedResult(ResponseResult.Success())
        useCase.changeSuccess(true)
        useCase.changeExpectedWeather(
            DetailsWeather(
                "35",
                "Saint Petersburg",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                "35",
                1,
            )
        )
        useCase.changeFlowValue()

        //init view model
        viewModel = DetailsViewModel(useCase)

        //check after init
        assertEquals(0, useCase.loadCalledList.size)
        assertEquals(1, useCase.getDetailsWeatherCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            DetailsWeather(
                "35", "Saint Petersburg", "35", "35", "35",
                "35", "35", "35", "35", "35", "35",
                "35", "35", "35", "35", 1
            ),
            viewModel.detailsWeather.value
        )

        //change expected weather
        useCase.changeExpectedWeather(
            DetailsWeather(
                "today", "Moscow", "25", "25", "25",
                "25", "25", "25", "25", "25", "25",
                "25", "25", "25", "25", 2
            )
        )
        useCase.changeFlowValue()

        //check after change
        assertEquals(0, useCase.loadCalledList.size)
        assertEquals(1, useCase.getDetailsWeatherCalledList.size)
        assertEquals(true, viewModel.viewState.value is ViewState.Success)
        assertEquals(
            DetailsWeather(
                "today", "Moscow", "25", "25", "25",
                "25", "25", "25", "25", "25", "25",
                "25", "25", "25", "25", 2
            ),
            viewModel.detailsWeather.value
        )
    }
}


private class TestDetailsWeatherUseCase : DetailsWeatherUseCase {

    private var result: ResponseResult = ResponseResult.Success()
    private var weather = DetailsWeather(
        "",
        "Saint Petersburg",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0
    )
    private var detailsWeatherFlow = MutableStateFlow(weather)
    private var success: Boolean = true

    val loadCalledList = mutableListOf<ResponseResult>()
    val getDetailsWeatherCalledList = mutableListOf<StateFlow<DetailsWeather>>()

    fun changeExpectedResult(newResult: ResponseResult) {
        result = newResult
    }

    fun changeExpectedWeather(newWeather: DetailsWeather) {
        weather = newWeather
    }

    fun changeSuccess(success: Boolean) {
        this.success = success
    }

    fun changeFlowValue() {
        detailsWeatherFlow.value = weather
    }

    override fun getDetailsWeather(): Flow<DetailsWeather> {
        getDetailsWeatherCalledList.add(detailsWeatherFlow)
        return detailsWeatherFlow.asStateFlow()
    }

    override suspend fun loadWeather(): ResponseResult {
        if (success) {
            changeFlowValue()
        }
        loadCalledList.add(result)
        return result
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