package com.example.weather.domain

import com.example.weather.data.repository.LocationRepository
import com.example.weather.extensions.Result
import com.example.weather.extensions.success
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
        whenever(locationRepository.getLocations("")).thenReturn(Result.Success(emptyList()))

        val result = getLocationUseCase.getLocations("").success()

        verify(locationRepository).getLocations("")
        assertThat(result, equalTo(emptyList()))
    }
}
