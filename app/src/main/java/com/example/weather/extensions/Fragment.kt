package com.example.weather.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT

fun Fragment.snackbar(text: Text, duration: Int = LENGTH_SHORT) =
        Snackbar.make(requireView(), text.getString(requireView().context), duration).apply { show() }

fun Fragment.snackbar(@StringRes resId: Int, duration: Int = LENGTH_SHORT) =
        Snackbar.make(requireView(), resId, duration).apply { show() }
