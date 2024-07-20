package com.example.weather.data.model

import kotlinx.serialization.SerialName

data class LocationApiResponse(@SerialName("id")
                               val id: Long? = null,
                               @SerialName("name")
                               val name: String? = null,
                               @SerialName("region")
                               val region: String? = null,
                               @SerialName("country")
                               val country: String? = null)
