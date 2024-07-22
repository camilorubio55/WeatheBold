package com.example.weather.extensions

fun String.Companion.empty() = ""

fun <R> String.ifNotEmpty(block: (String) -> R) {
    if (this.isNotEmpty()) block(this)
}
