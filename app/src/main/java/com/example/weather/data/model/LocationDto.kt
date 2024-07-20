package com.example.weather.data.model

import com.example.weather.domain.model.Location
import com.example.weather.extensions.orDefault
import kotlinx.serialization.SerialName

data class LocationApiResponse(@SerialName("id")
                               val id: Long? = null,
                               @SerialName("name")
                               val name: String? = null,
                               @SerialName("region")
                               val region: String? = null,
                               @SerialName("country")
                               val country: String? = null)

fun List<LocationApiResponse>.toLocations() = map { it.toLocation() }

fun LocationApiResponse.toLocation() = Location(
        id = id.orDefault(),
        name = name.orEmpty(),
        region = region.orEmpty(),
        country = country.orEmpty())
