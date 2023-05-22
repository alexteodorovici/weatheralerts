package com.alexteodorovici.weatheralerts.di
// Hilt module for providing dependencies.

import com.alexteodorovici.weatheralerts.data.network.ApiService
import com.alexteodorovici.weatheralerts.data.network.ImageDataSource
import com.alexteodorovici.weatheralerts.data.network.WeatherDataSource
import com.alexteodorovici.weatheralerts.data.repository.ImageRepository
import com.alexteodorovici.weatheralerts.data.repository.WeatherRepository
import com.alexteodorovici.weatheralerts.domain.usecase.FetchImageUseCase
import com.alexteodorovici.weatheralerts.domain.usecase.FetchWeatherUseCase
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
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideWeatherDataSource(apiService: ApiService): WeatherDataSource =
        WeatherDataSource(apiService)

    @Provides
    fun provideImageDataSource(): ImageDataSource =
        ImageDataSource()

    @Provides
    fun provideWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository =
        WeatherRepository(weatherDataSource)

    @Provides
    fun provideImageRepository(imageDataSource: ImageDataSource): ImageRepository =
        ImageRepository(imageDataSource)

    @Provides
    fun provideFetchWeatherUseCase(weatherRepository: WeatherRepository): FetchWeatherUseCase =
        FetchWeatherUseCase(weatherRepository)

    @Provides
    fun provideFetchImageUseCase(imageRepository: ImageRepository): FetchImageUseCase =
        FetchImageUseCase(imageRepository)
}
