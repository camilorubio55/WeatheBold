package com.example.weather

import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class MockWebServerRule : TestRule {

    private val server = MockWebServer()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                server.start()
                base.evaluate()
                server.shutdown()
            }
        }
    }

    fun configurationRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(server.url(DEFAULT_PATH))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    fun givenMockResponse(responseCode: Int = 200, fileName: String? = null) {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(responseCode)
        fileName.addBodyTo(mockResponse)
        server.enqueue(mockResponse)
    }

    private fun String?.addBodyTo(mockResponse: MockResponse) = this?.run {
        val fileResponse = readFile(this)
        mockResponse.setBody(fileResponse)
    }

    fun assertGetRequestSentTo(url: String) = getTakeRequest().run {
        assertThat(path, equalTo(url))
        assertThat(method, equalTo(GET))
    }

    fun assertPostRequestSentTo(url: String) = getTakeRequest().run {
        assertThat(path, equalTo(url))
        assertThat(method, equalTo(POST))
    }

    fun assertPatchRequestSentTo(url: String) = getTakeRequest().run {
        assertThat(path, equalTo(url))
        assertThat(method, equalTo(PATCH))
    }

    fun assertPutRequestSentTo(url: String) = getTakeRequest().run {
        assertThat(path, equalTo(url))
        assertThat(method, equalTo(PUT))
    }

    fun assertDeleteRequestSentTo(url: String) = getTakeRequest().run {
        assertThat(path, equalTo(url))
        assertThat(method, equalTo(DELETE))
    }

    private fun readFile(name: String) = this::class.java.classLoader?.getResource(name)?.readText().orEmpty()

    private fun getTakeRequest() = server.takeRequest()

    companion object {
        private const val DEFAULT_PATH = "/"
        private const val GET = "GET"
        private const val POST = "POST"
        private const val PATCH = "PATCH"
        private const val PUT = "PUT"
        private const val DELETE = "DELETE"
    }
}
