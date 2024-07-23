package com.example.weather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.CoroutineScopeRule
import com.example.weather.assertThatEquals
import com.example.weather.domain.GetLocationDetailUseCase
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.extensions.Result
import com.example.weather.fakedata.ANY_NAME
import com.example.weather.fakedata.givenLocationDetail
import com.example.weather.getOrAwaitValue
import com.example.weather.ui.locationDetail.LocationDetailUiModelState
import com.example.weather.ui.locationDetail.LocationDetailViewModel
import com.example.weather.ui.locationDetail.toLocationDetailUi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocationDetailViewModelShould {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineScopeRule = CoroutineScopeRule()

    private val getLocationDetailUseCase = mock<GetLocationDetailUseCase>()

    private lateinit var locationDetailViewModel: LocationDetailViewModel

    @Before
    fun setUp() {
        locationDetailViewModel = LocationDetailViewModel(
                getLocationDetailUseCase,
                coroutineScopeRule.coroutinesDispatchers)
    }

    @Test
    fun `Return location Detail when getLocationDetail is called`() = runTest {
        val locationsDetail = givenLocationDetail()
        val locationDetailUiModel = LocationDetailUiModelState(
                showProgress = false,
                isRefresh = false,
                locationDetail = locationsDetail.toLocationDetailUi(),
                exception = null)
        whenever(getLocationDetailUseCase.getLocationDetail(ANY_NAME)).thenReturn(Result.Success(locationsDetail))

        locationDetailViewModel.getLocationDetail(ANY_NAME)

        val locationDetailUiModelState = locationDetailViewModel.locationDetailUiModelState.getOrAwaitValue()
        verify(getLocationDetailUseCase).getLocationDetail(ANY_NAME)
        assertThatEquals(locationDetailUiModelState, locationDetailUiModel)
    }

    @Test
    fun `Return ConnectionNetwork Exception when getLocationDetail is called and fails`() = runTest {
        val connectionNetworkException = ConnectionNetwork()
        val locationDetailUiModel = LocationDetailUiModelState(
                showProgress = false,
                isRefresh = false,
                locationDetail = null,
                exception = connectionNetworkException)
        whenever(getLocationDetailUseCase.getLocationDetail(ANY_NAME)).thenReturn(Result.Error(connectionNetworkException))

        locationDetailViewModel.getLocationDetail(ANY_NAME)

        val locationDetailUiModelState = locationDetailViewModel.locationDetailUiModelState.getOrAwaitValue()
        verify(getLocationDetailUseCase).getLocationDetail(ANY_NAME)
        assertThatEquals(locationDetailUiModelState, locationDetailUiModel)
    }
}
