package com.example.weather.fakedata

import com.example.weather.domain.model.ConditionDetail
import com.example.weather.domain.model.CurrentDetail
import com.example.weather.domain.model.ForecastDetail
import com.example.weather.domain.model.Location
import com.example.weather.domain.model.LocationDetail

const val ANY_ID = 2774267L
const val ANY_NAME = "Text"
private const val ANY_REGION = "Eastern"
private const val ANY_COUNTRY = "Sambia"
private const val ANY_LAST_UPDATED = "2024-07-20 21:45"
private const val ANY_DATE = "2024-07-20"
private const val ANY_TEMPERATURE = "18.4"
private const val ANY_AVERAGE_TEMPERATURE = "20.4"
private const val ANY_WEATHER_CONDITION = "Clear"
private const val ANY_WEATHER_CONDITION_ICON = "https://cdn.weatherapi.com/weather/64x64/day/113.png"

fun givenLocations() = listOf(givenLocation())

private fun givenLocation() = Location(
        id = ANY_ID,
        name = ANY_NAME,
        region = ANY_REGION,
        country = ANY_COUNTRY
)

fun givenLocationDetail() = LocationDetail(
        name = ANY_NAME,
        currentDetail = givenCurrentDetail(),
        forecastDays = givenForecastDays()
)

private fun givenCurrentDetail() = CurrentDetail(
        lastUpdated = ANY_LAST_UPDATED,
        temperature = ANY_TEMPERATURE,
        condition = givenConditionDetail(),
)

private fun givenConditionDetail() = ConditionDetail(
        conditionText = ANY_WEATHER_CONDITION,
        conditionIcon = ANY_WEATHER_CONDITION_ICON
)

private fun givenForecastDays() = listOf(givenForecastDetail())

private fun givenForecastDetail() = ForecastDetail(
        date = ANY_DATE,
        averageTemperature = ANY_AVERAGE_TEMPERATURE,
        condition = givenConditionDetail()
)

