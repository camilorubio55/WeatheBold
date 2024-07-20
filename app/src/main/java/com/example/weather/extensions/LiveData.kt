package com.example.weather.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.liveDataObserve(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))
