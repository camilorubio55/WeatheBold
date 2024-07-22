package com.example.weather.domain

import com.example.weather.assertThatIsInstanceOf
import com.example.weather.data.repository.LocationRepository
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.extensions.Result
import com.example.weather.extensions.empty
import com.example.weather.extensions.error
import com.example.weather.extensions.success
import com.example.weather.fakedata.givenLocationDetail
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetLocationDetailUseCaseShould {

    private val locationRepository = mock<LocationRepository>()

    private lateinit var getLocationDetailUseCase: GetLocationDetailUseCase

    @Before
    fun setup() {
        getLocationDetailUseCase = GetLocationDetailUseCase(locationRepository)
    }

    @Test
    fun `Get location detail when getLocationDetail is called`() = runBlocking {
        val locationDetail = givenLocationDetail()
        whenever(locationRepository.getLocationDetail(String.empty())).thenReturn(Result.Success(locationDetail))

        val result = getLocationDetailUseCase.getLocationDetail(String.empty()).success()

        verify(locationRepository).getLocationDetail(String.empty())
        assertThat(result, equalTo(locationDetail))
    }

    @Test
    fun `Get Exception when getLocationDetail response is failure`() = runBlocking {
        whenever(locationRepository.getLocationDetail(String.empty())).thenReturn(Result.Error(ConnectionNetwork()))

        val result = getLocationDetailUseCase.getLocationDetail(String.empty()).error()

        verify(locationRepository).getLocationDetail(String.empty())
        assertThatIsInstanceOf<ConnectionNetwork>(result)
    }
}
