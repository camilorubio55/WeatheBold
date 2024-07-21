package com.example.weather.ui.locationDetail

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.databinding.WeatherItemBinding

class WeatherViewHolder(private val binding: WeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecastDetailUi: ForecastDetailUi) = forecastDetailUi.run {
        binding.textViewDate.text = date
        binding.textViewCondition.text = condition.conditionText
        binding.textViewTemperature.text = averageTemperature
        Glide.with(binding.container.rootView.context)
                .load(condition.conditionIcon)
                .circleCrop()
                .into(binding.imageViewWeather)
    }
}

