package com.example.weather.data

import com.example.weather.assertThatEquals
import com.example.weather.assertThatIsInstanceOf
import com.example.weather.data.datasource.LocationRemoteDataSource
import com.example.weather.data.repository.LocationRepository
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.extensions.Result
import com.example.weather.extensions.error
import com.example.weather.extensions.success
import com.example.weather.fakedata.ANY_NAME
import com.example.weather.fakedata.givenLocationDetail
import com.example.weather.fakedata.givenLocations
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocationRepositoryShould {

    private val locationRemoteDataSource = mock<LocationRemoteDataSource>()

    private lateinit var locationRepository: LocationRepository

    @Before
    fun setup() {
        locationRepository = LocationRepository(locationRemoteDataSource)
    }

    @Test
    fun `Get Locations when getLocations is called`(): Unit = runBlocking {
        val locations = givenLocations()
        whenever(locationRemoteDataSource.getLocations(ANY_NAME)).thenReturn(Result.Success(locations))

        val result = locationRepository.getLocations(ANY_NAME).success()

        verify(locationRemoteDataSource).getLocations(ANY_NAME)
        assertThatEquals(result, locations)
    }

    @Test
    fun `Get Exception when getLocations response is failure`() = runBlocking {
        whenever(locationRemoteDataSource.getLocations(ANY_NAME)).thenReturn(Result.Error(ConnectionNetwork()))

        val result = locationRepository.getLocations(ANY_NAME).error()

        verify(locationRemoteDataSource).getLocations(ANY_NAME)
        assertThatIsInstanceOf<ConnectionNetwork>(result)
    }

    @Test
    fun `Get location detail when getLocationDetail is called`() = runBlocking {
        val locationDetail = givenLocationDetail()
        whenever(locationRemoteDataSource.getLocationDetail(ANY_NAME)).thenReturn(Result.Success(locationDetail))

        val result = locationRepository.getLocationDetail(ANY_NAME).success()

        verify(locationRemoteDataSource).getLocationDetail(ANY_NAME)
        assertThatEquals(result, locationDetail)
    }

    @Test
    fun `Get Exception when getLocationDetail response is failure`() = runBlocking {
        whenever(locationRemoteDataSource.getLocationDetail(ANY_NAME)).thenReturn(Result.Error(ConnectionNetwork()))

        val result = locationRepository.getLocationDetail(ANY_NAME).error()

        verify(locationRemoteDataSource).getLocationDetail(ANY_NAME)
        assertThatIsInstanceOf<ConnectionNetwork>(result)
    }
}
