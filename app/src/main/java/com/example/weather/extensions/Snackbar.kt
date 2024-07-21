package com.example.weather.extensions

import com.example.weather.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.showError() = apply { view.setBackgroundResource(R.color.red) }
