package com.example.weather.domain

import com.example.weather.assertThatIsInstanceOf
import com.example.weather.data.repository.LocationRepository
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.extensions.Result
import com.example.weather.extensions.empty
import com.example.weather.extensions.error
import com.example.weather.extensions.success
import com.example.weather.fakedata.givenLocations
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetLocationUseCaseShould {

    private val locationRepository = mock<LocationRepository>()

    private lateinit var getLocationUseCase: GetLocationUseCase

    @Before
    fun setup() {
        getLocationUseCase = GetLocationUseCase(locationRepository)
    }

    @Test
    fun `Get location list when getLocations is called`() = runBlocking {
        val locations = givenLocations()
        whenever(locationRepository.getLocations(String.empty())).thenReturn(Result.Success(locations))

        val result = getLocationUseCase.getLocations(String.empty()).success()

        verify(locationRepository).getLocations(String.empty())
        assertThat(result, equalTo(locations))
    }

    @Test
    fun `Get Exception when getLocations response is failure`() = runBlocking {
        whenever(locationRepository.getLocations(String.empty())).thenReturn(Result.Error(ConnectionNetwork()))

        val result = getLocationUseCase.getLocations(String.empty()).error()

        verify(locationRepository).getLocations(String.empty())
        assertThatIsInstanceOf<ConnectionNetwork>(result)
    }
}
