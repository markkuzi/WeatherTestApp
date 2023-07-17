package com.example.forecast.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.BaseFragment
import com.example.core.ViewState
import com.example.core.onTryAgain
import com.example.core.setSimpleViewStatusVisibility
import com.example.forecast.R
import com.example.forecast.databinding.FragmentForecastBinding
import com.example.forecast.presentation.adapter.ForecastListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : BaseFragment(R.layout.fragment_forecast) {

    private val viewModel by viewModel<ForecastViewModel>()
    private val binding by viewBinding(FragmentForecastBinding::bind)
    private val forecastAdapter by lazy { ForecastListAdapter() }
    private var cityName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityName = arguments?.getString(CITY_NAME)
        viewModel.loadWeather(cityName ?: "")
        binding.tvCityName.text = cityName
        binding.rvForecast.adapter = forecastAdapter

        viewModel.forecastWeather.observe(viewLifecycleOwner) {
            forecastAdapter.submitList(it.weatherList)
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            setSimpleViewStatusVisibility(root = binding.root, state = it)
            if (it is ViewState.Error)
                binding.btnBack.visibility = View.VISIBLE
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.refreshWeather(cityName ?: "")
        }

        onTryAgain(root = binding.root) {
            viewModel.refreshWeather(cityName ?: "")
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
    companion object {
        private const val CITY_NAME = "cityName"
    }
}

