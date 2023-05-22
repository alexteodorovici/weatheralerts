// Class to fetch weather data from the network.
package com.alexteodorovici.weatheralerts.data.network

import android.util.Log
import com.alexteodorovici.weatheralerts.data.model.AlertResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

//this class is responsible for fetching weather data from the network
class WeatherDataSource {

    //use the Moshi library to deserialize the JSON responses from the API
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //log the network request and response headers for debugging purposes
    private val loggingInterceptor: Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    //make the HTTP requests
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weather.gov/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    private val weatherApiService: ApiService = retrofit.create(ApiService::class.java)

    //suspend function that performs the network request to get the weather alerts
    //perform the network request on the IO dispatcher
    suspend fun fetchWeatherAlerts(): AlertResponse? = withContext(Dispatchers.IO) {
        try {
            val response = weatherApiService.getActiveAlerts()
            if (response.isSuccessful) {
                val alertResponse = response.body()
                Log.d("WeatherDataSource", "fetchWeatherAlerts - AlertResponse: $alertResponse")
                alertResponse
            } else {
                Log.e("WeatherDataSource", "fetchWeatherAlerts - Request unsuccessful: ${response.code()}")
                null
            }
        } catch (e: HttpException) {
            Log.e("WeatherDataSource", "fetchWeatherAlerts - HTTP exception: ${e.message()}")
            null
        } catch (e: IOException) {
            Log.e("WeatherDataSource", "fetchWeatherAlerts - IO exception: ${e.message}")
            null
        }
    }
}

