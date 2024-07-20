package com.example.weather.data.repository

import com.example.weather.data.datasource.LocationRemoteDataSource
import javax.inject.Inject

class LocationRepository @Inject constructor(private val locationRemoteDataSource: LocationRemoteDataSource) {

    suspend fun getLocations(text: String) = locationRemoteDataSource.getLocations(text)

    suspend fun getLocationDetail(name: String) = locationRemoteDataSource.getLocationDetail(name)

}
