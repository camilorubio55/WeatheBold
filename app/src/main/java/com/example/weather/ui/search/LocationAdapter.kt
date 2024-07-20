package com.example.weather.ui.search

import android.view.ViewGroup
import com.example.weather.databinding.LocationItemBinding
import com.example.weather.extensions.RecyclerAdapter
import com.example.weather.extensions.getLayoutInflater

class LocationAdapter : RecyclerAdapter<LocationUi, LocationViewHolder>() {

    var onLocationListener: ((name: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            LocationViewHolder(
                    LocationItemBinding.inflate(parent.getLayoutInflater(), parent, false), onLocationListener)

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        item(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = size()
}
