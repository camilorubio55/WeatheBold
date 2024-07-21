package com.example.weather.ui.locationDetail

import com.example.weather.domain.model.ConditionDetail
import com.example.weather.domain.model.CurrentDetail
import com.example.weather.domain.model.ForecastDetail
import com.example.weather.domain.model.LocationDetail

data class LocationDetailUiModelState(
        val showProgress: Boolean,
        val isRefresh: Boolean,
        val locationDetail: LocationDetailUi?,
        val exception: Exception?)

data class LocationDetailUi(
        val name: String,
        val currentDetail: CurrentDetailUi,
        val forecastDays: List<ForecastDetailUi>)

data class CurrentDetailUi(
        val lastUpdated: String,
        val temperature: String,
        val condition: ConditionDetailUi)

data class ConditionDetailUi(
        val conditionText: String,
        val conditionIcon: String)

data class ForecastDetailUi(
        val date: String,
        val averageTemperature: String,
        val condition: ConditionDetailUi)

fun LocationDetail.toLocationDetailUi() = LocationDetailUi(
        name = name,
        currentDetail = currentDetail.toCurrentDetailUi(),
        forecastDays = forecastDays.toForecastDetailUiList())

private fun CurrentDetail.toCurrentDetailUi() = CurrentDetailUi(
        lastUpdated = lastUpdated,
        temperature = "$temperature °C",
        condition = condition.toConditionDetailUi())

private fun ConditionDetail.toConditionDetailUi() = ConditionDetailUi(
        conditionText = conditionText,
        conditionIcon = conditionIcon)

private fun List<ForecastDetail>.toForecastDetailUiList() = map { it.toForecastDetailUi() }

private fun ForecastDetail.toForecastDetailUi() = ForecastDetailUi(
        date = date,
        averageTemperature = "$averageTemperature °C",
        condition = condition.toConditionDetailUi())
