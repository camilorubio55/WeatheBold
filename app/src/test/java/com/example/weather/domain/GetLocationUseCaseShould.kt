package com.example.weather.domain

import com.example.weather.assertThatEquals
import com.example.weather.assertThatIsInstanceOf
import com.example.weather.data.repository.LocationRepository
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.extensions.Result
import com.example.weather.extensions.error
import com.example.weather.extensions.success
import com.example.weather.fakedata.ANY_NAME
import com.example.weather.fakedata.givenLocations
import kotlinx.coroutines.runBlocking
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
        whenever(locationRepository.getLocations(ANY_NAME)).thenReturn(Result.Success(locations))

        val result = getLocationUseCase.getLocations(ANY_NAME).success()

        verify(locationRepository).getLocations(ANY_NAME)
        assertThatEquals(result, locations)
    }

    @Test
    fun `Get Exception when getLocations response is failure`() = runBlocking {
        whenever(locationRepository.getLocations(ANY_NAME)).thenReturn(Result.Error(ConnectionNetwork()))

        val result = getLocationUseCase.getLocations(ANY_NAME).error()

        verify(locationRepository).getLocations(ANY_NAME)
        assertThatIsInstanceOf<ConnectionNetwork>(result)
    }
}
