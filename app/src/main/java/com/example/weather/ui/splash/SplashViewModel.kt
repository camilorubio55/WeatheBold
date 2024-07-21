package com.example.weather.ui.splash

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.extensions.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _navigateToSearchFragment = MutableLiveData<Event<Unit>>()

    val navigateToSearchFragment: LiveData<Event<Unit>>
        get() = _navigateToSearchFragment

    fun navigateToSearchFragment() {
        object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long)= Unit

            override fun onFinish() {
                _navigateToSearchFragment.value = Event(Unit)
            }
        }.start()
    }
}
