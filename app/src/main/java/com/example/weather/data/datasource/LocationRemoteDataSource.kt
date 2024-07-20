package com.example.weather.data.datasource

import android.util.Log
import com.example.weather.data.LocationApi
import com.example.weather.data.WEATHER_KEY
import javax.inject.Inject
import com.example.weather.extensions.Result

class LocationRemoteDataSource @Inject constructor(private val locationApi: LocationApi) {

    suspend fun getLocations(text: String) = try {
        val result = locationApi.getLocations(key = WEATHER_KEY, text = text)
        Log.d("---Test Endpoint", result.first().name.toString())
        Result.Success(true)
    } catch (e: Exception) {
        Log.d("---Test Endpoint", "Bad Response")
        Result.Error(Exception())
    }
}
