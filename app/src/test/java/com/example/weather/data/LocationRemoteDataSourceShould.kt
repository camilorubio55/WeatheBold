package com.example.weather.data

import com.example.weather.MockWebServerRule
import com.example.weather.assertThatEquals
import com.example.weather.assertThatIsInstanceOf
import com.example.weather.data.datasource.LocationRemoteDataSource
import com.example.weather.exceptions.ApiExceptionHandler
import com.example.weather.exceptions.ApiExceptionHandler.Companion.AUTHENTICATION_CODE
import com.example.weather.exceptions.ApiRequestException
import com.example.weather.extensions.error
import com.example.weather.extensions.success
import com.example.weather.fakedata.ANY_NAME
import com.example.weather.fakedata.givenLocationDetail
import com.example.weather.fakedata.givenLocations
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.create

class LocationRemoteDataSourceShould {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    private val apiExceptionHandler = ApiExceptionHandler()

    private lateinit var locationRemoteDataSource: LocationRemoteDataSource

    @Before
    fun setup() {
        val configurationRetrofit = mockWebServerRule.configurationRetrofit()

        locationRemoteDataSource = LocationRemoteDataSource(
                locationApi = configurationRetrofit.create(),
                apiExceptionHandler = apiExceptionHandler)
    }

    @Test
    fun `Verify GET method when getLocations is called`() = runBlocking {
        mockWebServerRule.givenMockResponse()

        locationRemoteDataSource.getLocations(ANY_NAME)

        mockWebServerRule.assertGetRequestSentTo("/search.json?key=de5553176da64306b86153651221606&q=Text")
    }

    @Test
    fun `Get Location task when getLocations is called`() = runBlocking {
        val locations = givenLocations()
        mockWebServerRule.givenMockResponse(fileName = "endpoints-responses/get-locations-response.json")

        val result = locationRemoteDataSource.getLocations(ANY_NAME).success()

        assertThatEquals(result, locations)
    }

    @Test
    fun `Return Api Request Exception when getLocations throws connection error occurred`() = runBlocking {
        mockWebServerRule.givenMockResponse(responseCode = AUTHENTICATION_CODE)

        val result = locationRemoteDataSource.getLocations(ANY_NAME).error()

        assertThatIsInstanceOf<ApiRequestException>(result)
    }

    @Test
    fun `Verify GET method when getLocationDetail is called`() = runBlocking {
        mockWebServerRule.givenMockResponse()

        locationRemoteDataSource.getLocationDetail(ANY_NAME)

        mockWebServerRule.assertGetRequestSentTo("/forecast.json?key=de5553176da64306b86153651221606&q=Text&days=3")
    }

    @Test
    fun `Get Location task when getLocationDetail is called`() = runBlocking {
        val locationDetail = givenLocationDetail()
        mockWebServerRule.givenMockResponse(fileName = "endpoints-responses/get-location-detail-response.json")

        val result = locationRemoteDataSource.getLocationDetail(ANY_NAME).success()

        assertThatEquals(result, locationDetail)
    }

    @Test
    fun `Return Api Request Exception when getLocationDetail throws connection error occurred`() = runBlocking {
        mockWebServerRule.givenMockResponse(responseCode = AUTHENTICATION_CODE)

        val result = locationRemoteDataSource.getLocationDetail(ANY_NAME).error()

        assertThatIsInstanceOf<ApiRequestException>(result)
    }
}
