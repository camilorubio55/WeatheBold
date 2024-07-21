package com.example.weather.data.model

import com.example.weather.domain.model.ConditionDetail
import com.example.weather.domain.model.CurrentDetail
import com.example.weather.domain.model.ForecastDetail
import com.example.weather.domain.model.Location
import com.example.weather.domain.model.LocationDetail
import com.example.weather.extensions.orDefault

data class LocationApiResponse(val id: Long? = null,
                               val name: String? = null,
                               val region: String? = null,
                               val country: String? = null)

data class LocationDetailApiResponse(val location: LocationApiResponse? = null,
                                     val current: CurrentDetailApiResponse? = null,
                                     val forecast: ForecastDetailApiResponse? = null)

data class CurrentDetailApiResponse(val last_updated: String? = null,
                                    val temp_c: String? = null,
                                    val condition: ConditionDetailApiResponse? = null)

data class ConditionDetailApiResponse(val text: String? = null,
                                      val icon: String? = null,
                                      val code: Long? = null)

data class ForecastDetailApiResponse(val forecastday: List<ForecastDayDetailApiResponse>? = null)

data class ForecastDayDetailApiResponse(val date: String? = null,
                                        val day: DayDetailApiResponse? = null)

data class DayDetailApiResponse(val avgtemp_c: String? = null,
                                val condition: ConditionDetailApiResponse? = null)

fun List<LocationApiResponse>.toLocations() = map { it.toLocation() }

fun LocationApiResponse.toLocation() = Location(
        id = id.orDefault(),
        name = name.orEmpty(),
        region = region.orEmpty(),
        country = country.orEmpty())

fun LocationDetailApiResponse.toLocationDetail() = LocationDetail(
        name = location?.name.orEmpty(),
        currentDetail = current.toCurrentDetail(),
        forecastDays = forecast?.forecastday?.map { it.toForecastDetail() }.orEmpty())

fun CurrentDetailApiResponse?.toCurrentDetail() = CurrentDetail(
        lastUpdated = this?.last_updated.orEmpty(),
        temperature = this?.temp_c.orEmpty(),
        condition = this?.condition.toConditionDetail())

fun ConditionDetailApiResponse?.toConditionDetail() = ConditionDetail(
        conditionText = this?.text.orEmpty(),
        conditionIcon = "https:${this?.icon}")

fun ForecastDayDetailApiResponse?.toForecastDetail() = ForecastDetail(
        date = this?.date.orEmpty(),
        averageTemperature = this?.day?.avgtemp_c.orEmpty(),
        condition = this?.day?.condition.toConditionDetail())
