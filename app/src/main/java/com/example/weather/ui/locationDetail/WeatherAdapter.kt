package com.example.weather.ui.locationDetail

import android.view.ViewGroup
import com.example.weather.databinding.WeatherItemBinding
import com.example.weather.extensions.RecyclerAdapter
import com.example.weather.extensions.getLayoutInflater

class WeatherAdapter : RecyclerAdapter<ForecastDetailUi, WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WeatherViewHolder(
                    WeatherItemBinding.inflate(parent.getLayoutInflater(), parent, false))

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        item(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = size()
}
