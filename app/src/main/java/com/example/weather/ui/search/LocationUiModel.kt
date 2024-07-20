package com.example.weather.ui.search

import com.example.weather.domain.model.Location

data class LocationUiModelState(
        val showProgress: Boolean,
        val isRefresh: Boolean,
        val locations: List<LocationUi>,
        val exception: Exception?)

data class LocationUi(
        val id: Long,
        val name: String,
        val country: String)

fun List<Location>.toLocationUiList() = map { it.toLocationUi() }

private fun Location.toLocationUi() = LocationUi(
        id = id,
        name = name,
        country = country)
