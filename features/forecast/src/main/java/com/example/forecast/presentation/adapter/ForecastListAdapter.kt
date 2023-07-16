package com.example.forecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.forecast.databinding.ForecastItemBinding
import com.example.forecast.domain.entity.WeatherList

class ForecastListAdapter :
    ListAdapter<WeatherList, ForecastViewHolder>(ForecastItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val weather = getItem(position)

        with(holder.binding) {
            tvTemp.text = weather.temp
            tvDate.text = weather.date
            tvDescription.text= weather.main
            ivWeatherIcon.setImageResource(weather.icon)
        }

    }
}