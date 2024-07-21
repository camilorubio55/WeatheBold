package com.example.weather.di

import com.example.weather.core.CoroutinesDispatchers
import com.example.weather.data.BASE_URL
import com.example.weather.data.LocationApi
import com.example.weather.exceptions.ApiExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MainActivityModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient) : LocationApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(LocationApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApiExceptionHandler() : ApiExceptionHandler {
        return ApiExceptionHandler()
    }

    @Provides
    @Singleton
    fun provideCoroutinesDispatchers() = CoroutinesDispatchers()
}
