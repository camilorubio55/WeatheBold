package com.example.weather.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
        private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    fun getLocations() {
        viewModelScope.launch(IO) {
            getLocationUseCase.getLocations("Text")
        }
    }
}
