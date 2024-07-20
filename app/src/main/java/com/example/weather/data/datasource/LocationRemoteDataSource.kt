package com.example.weather.data.datasource

import com.example.weather.data.DAYS_NUMBER
import com.example.weather.data.LocationApi
import com.example.weather.data.WEATHER_KEY
import com.example.weather.data.model.toLocationDetail
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

    suspend fun getLocationDetail(name: String) = try {
        val result = locationApi.getLocationDetail(key = WEATHER_KEY, name = name, days = DAYS_NUMBER)
        Result.Success(result.toLocationDetail())
    } catch (e: Exception) {
        Result.Error(Exception())
    }
}
