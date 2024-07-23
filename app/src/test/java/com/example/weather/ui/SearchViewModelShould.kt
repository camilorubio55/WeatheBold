package com.example.weather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.CoroutineScopeRule
import com.example.weather.assertThatEquals
import com.example.weather.extensions.Result
import com.example.weather.domain.GetLocationUseCase
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.fakedata.ANY_NAME
import com.example.weather.fakedata.givenLocations
import com.example.weather.getOrAwaitValue
import com.example.weather.ui.search.LocationUiModelState
import com.example.weather.ui.search.SearchViewModel
import com.example.weather.ui.search.toLocationUiList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchViewModelShould {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineScopeRule = CoroutineScopeRule()

    private val getLocationUseCase = mock<GetLocationUseCase>()

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(
                getLocationUseCase,
                coroutineScopeRule.coroutinesDispatchers)
    }

    @Test
    fun `Navigate to location Detail when navigateToLocationDetail is called`() = runTest {
        searchViewModel.navigateToLocationDetail(ANY_NAME)

        val searchActionState = searchViewModel.navigateToLocationDetail.value?.peek()
        assertThatEquals(searchActionState, ANY_NAME)
    }

    @Test
    fun `Return location List when getLocations is called`() = runTest {
        val locations = givenLocations()
        val locationListUiModel = LocationUiModelState(
                showProgress = false,
                isRefresh = false,
                locations = locations.toLocationUiList(),
                exception = null)
        whenever(getLocationUseCase.getLocations(ANY_NAME)).thenReturn(Result.Success(locations))

        searchViewModel.getLocations(ANY_NAME)

        val locationUiModelState = searchViewModel.locationUiModelState.getOrAwaitValue()
        verify(getLocationUseCase).getLocations(ANY_NAME)
        assertThatEquals(locationUiModelState, locationListUiModel)
    }

    @Test
    fun `Return ConnectionNetwork Exception when getLocations is called and fails`() = runTest {
        val connectionNetworkException = ConnectionNetwork()
        val locationListUiModel = LocationUiModelState(
                showProgress = false,
                isRefresh = false,
                locations = emptyList(),
                exception = connectionNetworkException)
        whenever(getLocationUseCase.getLocations(ANY_NAME)).thenReturn(Result.Error(connectionNetworkException))

        searchViewModel.getLocations(ANY_NAME)

        val locationUiModelState = searchViewModel.locationUiModelState.getOrAwaitValue()
        verify(getLocationUseCase).getLocations(ANY_NAME)
        assertThatEquals(locationUiModelState, locationListUiModel)
    }
}
