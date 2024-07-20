package com.example.weather.extensions

private const val DEFAULT_VALUE = 0L

fun Long?.orDefault() = this ?: DEFAULT_VALUE
