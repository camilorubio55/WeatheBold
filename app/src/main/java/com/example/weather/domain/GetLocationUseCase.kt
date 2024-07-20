package com.example.weather.domain

import com.example.weather.data.repository.LocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {

    suspend fun getLocations(text: String) = locationRepository.getLocations(text)
}
