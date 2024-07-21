package com.example.weather.extensions

fun <R> String.ifNotEmpty(block: (String) -> R) {
    if (this.isNotEmpty()) block(this)
}
