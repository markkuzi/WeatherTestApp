package com.example.details.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.BaseFragment
import com.example.core.ViewState
import com.example.core.onTryAgain
import com.example.core.setSimpleViewStatusVisibility
import com.example.details.R
import com.example.details.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private val viewModel by viewModel<DetailsViewModel>()
    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.detailsWeather.observe(viewLifecycleOwner) {
            with(binding) {
                tvCityName.text = it.name
                tvTemp.text = it.temp
                tvUpdateTime.text = it.date
                ivIcon.setImageResource(it.icon)
                tvDescription.text = it.weatherDescription
                tvFeelsLike.text = it.feelsLike
                tvTempMin.text = it.tempMin
                tvTempMax.text = it.tempMax
                tvHumidity.text = it.humidity
                tvPressure.text = it.pressure
                tvWindSpeed.text = it.windSpeed
                tvWindGust.text = it.windGust
                tvWindDeg.text = it.windDeg
                tvSunrise.text = it.sunrise
                tvSunset.text = it.sunset
            }

            onTryAgain(binding.root) {
                viewModel.loadWeather()
            }

            binding.btnRefresh.setOnClickListener { _ ->
                viewModel.loadWeather()
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            setSimpleViewStatusVisibility(root = binding.root, state = it)
            if (it is ViewState.Error)
                binding.btnBack.visibility = View.VISIBLE
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}