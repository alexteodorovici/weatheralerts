package com.alexteodorovici.weatheralerts.di
// Hilt module for providing dependencies.

import com.alexteodorovici.weatheralerts.data.network.ApiService
import com.alexteodorovici.weatheralerts.data.network.WeatherDataSource
import com.alexteodorovici.weatheralerts.data.repository.WeatherRepository
import com.alexteodorovici.weatheralerts.domain.mapper.AlertMapper
import com.alexteodorovici.weatheralerts.domain.usecase.FetchWeatherAlertsUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weather.gov/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideWeatherDataSource(): WeatherDataSource =
        WeatherDataSource()

    @Provides
    fun provideWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository =
        WeatherRepository(weatherDataSource)

    @Provides
    fun provideFetchWeatherAlertsUseCase(weatherRepository: WeatherRepository): FetchWeatherAlertsUseCase =
        FetchWeatherAlertsUseCase(weatherRepository)

    @Provides
    fun provideAlertMapper(): AlertMapper = AlertMapper()
}
