package com.alexteodorovici.weatheralerts.data.repository

import com.alexteodorovici.weatheralerts.data.model.AlertResponse
import com.alexteodorovici.weatheralerts.data.network.WeatherDataSource
import javax.inject.Inject

//this class is responsible for handling the data operations related to weather alerts.
// It acts as a single source of truth for accessing and managing weather alert data.
class WeatherRepository @Inject constructor(
    //provide the actual implementation for fetching weather data from the network
    private val dataSource: WeatherDataSource
) {
    suspend fun getWeatherAlerts(): AlertResponse? {
        //return the response received from the data source
        return dataSource.fetchWeatherAlerts()
    }
}
