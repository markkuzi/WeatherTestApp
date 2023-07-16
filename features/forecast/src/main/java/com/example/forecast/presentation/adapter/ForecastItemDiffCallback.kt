package com.example.forecast.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.forecast.domain.entity.WeatherList

class ForecastItemDiffCallback : DiffUtil.ItemCallback<WeatherList>() {
    override fun areItemsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: WeatherList, newItem: WeatherList): Boolean {
        return oldItem == newItem
    }
}
