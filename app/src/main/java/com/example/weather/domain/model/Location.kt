package com.example.weather.domain.model

data class Location (
        val id: Long,
        val name: String,
        val region: String,
        val country: String)

data class LocationDetail(
        val name: String,
        val currentDetail: CurrentDetail,
        val forecastDays: List<ForecastDetail>)

data class CurrentDetail(
        val lastUpdated: String,
        val temperature: String,
        val condition: ConditionDetail)

data class ConditionDetail(
        val conditionText: String,
        val conditionIcon: String)

data class ForecastDetail(
        val date: String,
        val averageTemperature: String,
        val condition: ConditionDetail)
