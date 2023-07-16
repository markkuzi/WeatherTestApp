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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvForecast.adapter = forecastAdapter

        viewModel.forecastWeather.observe(viewLifecycleOwner) {
            forecastAdapter.submitList(it.weatherList)
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            setSimpleViewStatusVisibility(root = binding.root, state = it)
        }

        onTryAgain(root = binding.root) {
            viewModel.loadForecastWeather("санкт петербург")
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}