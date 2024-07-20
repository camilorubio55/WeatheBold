package com.example.weather.ui.locationDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.GetLocationDetailUseCase
import com.example.weather.domain.model.LocationDetail
import com.example.weather.extensions.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
        private val getLocationDetailUseCase: GetLocationDetailUseCase
) : ViewModel() {

    private val _locationDetailUiModelState = MutableLiveData<LocationDetailUiModelState>()

    val locationDetailUiModelState: LiveData<LocationDetailUiModelState>
        get() = _locationDetailUiModelState

    fun getLocationDetail(name: String) {
        viewModelScope.launch(IO) {
            val result = getLocationDetailUseCase.getLocationDetail(name)
            withContext(Main) {
                when (result) {
                    is Result.Success -> getLocationDetailSuccess(result.data)
                    is Result.Error -> getLocationDetailError(result.exception)
                }
            }
        }
    }

    private fun getLocationDetailSuccess(locationDetail: LocationDetail) {
        emitLocationDetailUiModelState(locationDetail = locationDetail.toLocationDetailUi())
    }


    private fun getLocationDetailError(exception: Exception) {
        exception.printStackTrace()
        emitLocationDetailUiModelState(exception = exception)
    }

    private fun emitLocationDetailUiModelState(showProgress: Boolean = false,
                                               isRefresh: Boolean = false,
                                               locationDetail: LocationDetailUi? = null,
                                               exception: Exception? = null) {
        val locationDetailUiModelState = LocationDetailUiModelState(showProgress, isRefresh, locationDetail, exception)
        _locationDetailUiModelState.value = locationDetailUiModelState
    }
}
