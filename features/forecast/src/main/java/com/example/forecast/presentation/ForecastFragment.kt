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
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : BaseFragment(R.layout.fragment_forecast) {

    private val viewModel by viewModel<ForecastViewModel>()
    private val binding by viewBinding(FragmentForecastBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.forecastWeather.observe(viewLifecycleOwner) {
            if (it.cityName != "")
                binding.backBtn.text = it.cityName
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            setSimpleViewStatusVisibility(root = binding.root, state = it)
        }

        onTryAgain(root = binding.root) {
            viewModel.loadForecastWeather("санкт петербург")
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}