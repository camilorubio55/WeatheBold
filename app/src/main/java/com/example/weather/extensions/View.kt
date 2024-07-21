package com.example.weather.extensions

import android.view.LayoutInflater
import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.hideOrShow(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.getLayoutInflater(): LayoutInflater = LayoutInflater.from(context)
