package com.example.weather.data

import com.example.weather.data.model.LocationApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET(LOCATION_ENDPOINT)
    suspend fun getLocations(
            @Query(KEY_QUERY) key: String,
            @Query(Q_QUERY) text: String
    ): List<LocationApiResponse>
}
