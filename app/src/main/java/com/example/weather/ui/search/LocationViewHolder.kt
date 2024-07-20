package com.example.weather.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.LocationItemBinding

class LocationViewHolder(private val binding: LocationItemBinding,
                         private val onLocationListener: ((Long) -> Unit)?)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(locationUi: LocationUi) = locationUi.run {
        binding.textViewName.text = name
        binding.textViewCountry.text = country
        binding.root.setOnClickListener { onLocationListener?.invoke(1) }
    }
}
