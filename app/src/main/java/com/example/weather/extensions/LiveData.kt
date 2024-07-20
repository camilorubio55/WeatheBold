package com.example.weather.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.liveDataObserve(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <T : Any, L : LiveData<Event<T>>> LifecycleOwner.liveEventObserve(liveData: L, onEventUnconsumedContent: (T) -> Unit) =
        liveData.observe(this, EventObserver(onEventUnconsumedContent))
