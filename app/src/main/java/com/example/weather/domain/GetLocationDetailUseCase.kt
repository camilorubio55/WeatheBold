package com.example.weather.domain

import com.example.weather.data.repository.LocationRepository
import javax.inject.Inject

class GetLocationDetailUseCase @Inject constructor(private val locationRepository: LocationRepository) {

    suspend fun getLocationDetail(name: String) = locationRepository.getLocationDetail(name)
}
