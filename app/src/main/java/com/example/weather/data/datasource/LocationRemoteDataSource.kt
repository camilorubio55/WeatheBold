package com.example.weather.data.datasource

import com.example.weather.data.LocationApi
import com.example.weather.data.WEATHER_KEY
import com.example.weather.data.model.toLocations
import javax.inject.Inject
import com.example.weather.extensions.Result

class LocationRemoteDataSource @Inject constructor(private val locationApi: LocationApi) {

    suspend fun getLocations(text: String) = try {
        val result = locationApi.getLocations(key = WEATHER_KEY, text = text)
        Result.Success(result.toLocations())
    } catch (e: Exception) {
        Result.Error(Exception())
    }
}
