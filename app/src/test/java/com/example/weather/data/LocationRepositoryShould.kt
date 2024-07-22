package com.example.weather.data

import com.example.weather.assertThatEquals
import com.example.weather.assertThatIsInstanceOf
import com.example.weather.data.datasource.LocationRemoteDataSource
import com.example.weather.data.repository.LocationRepository
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.extensions.empty
import com.example.weather.extensions.success
import com.example.weather.extensions.Result
import com.example.weather.extensions.error
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
        whenever(locationRemoteDataSource.getLocations(String.empty())).thenReturn(Result.Success(locations))

        val result = locationRepository.getLocations(String.empty()).success()

        verify(locationRemoteDataSource).getLocations(String.empty())
        assertThatEquals(result, locations)
    }

    @Test
    fun `Get Exception when getLocations response is failure`() = runBlocking {
        whenever(locationRemoteDataSource.getLocations(String.empty())).thenReturn(Result.Error(ConnectionNetwork()))

        val result = locationRepository.getLocations(String.empty()).error()

        verify(locationRemoteDataSource).getLocations(String.empty())
        assertThatIsInstanceOf<ConnectionNetwork>(result)
    }

    @Test
    fun `Get location detail when getLocationDetail is called`() = runBlocking {
        val locationDetail = givenLocationDetail()
        whenever(locationRemoteDataSource.getLocationDetail(String.empty())).thenReturn(Result.Success(locationDetail))

        val result = locationRepository.getLocationDetail(String.empty()).success()

        verify(locationRemoteDataSource).getLocationDetail(String.empty())
        assertThatEquals(result, locationDetail)
    }

    @Test
    fun `Get Exception when getLocationDetail response is failure`() = runBlocking {
        whenever(locationRemoteDataSource.getLocationDetail(String.empty())).thenReturn(Result.Error(ConnectionNetwork()))

        val result = locationRepository.getLocationDetail(String.empty()).error()

        verify(locationRemoteDataSource).getLocationDetail(String.empty())
        assertThatIsInstanceOf<ConnectionNetwork>(result)
    }
}
