package com.example.weather.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.GetLocationUseCase
import com.example.weather.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.weather.extensions.Result


@HiltViewModel
class SearchViewModel @Inject constructor(
        private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private val _locationUiModelState = MutableLiveData<LocationUiModelState>()

    val locationUiModelState: LiveData<LocationUiModelState>
        get() = _locationUiModelState

    fun getLocations(text: String) {
        viewModelScope.launch(IO) {
            val result = getLocationUseCase.getLocations(text)
            withContext(Main) {
                when (result) {
                    is Result.Success -> getLocationsSuccess(result.data)
                    is Result.Error -> getLocationsError(result.exception)
                }
            }
        }
    }

    private fun getLocationsSuccess(locations: List<Location>) = emitLocationUiModelState(locations = locations.toLocationUiList())

    private fun getLocationsError(exception: Exception) {
        exception.printStackTrace()
        emitLocationUiModelState(exception = exception)
    }

    private fun emitLocationUiModelState(showProgress: Boolean = false,
                                         isRefresh: Boolean = false,
                                         locations: List<LocationUi> = emptyList(),
                                         exception: Exception? = null) {
        val locationUiModelState = LocationUiModelState(showProgress, isRefresh, locations, exception)
        _locationUiModelState.value = locationUiModelState
    }
}
