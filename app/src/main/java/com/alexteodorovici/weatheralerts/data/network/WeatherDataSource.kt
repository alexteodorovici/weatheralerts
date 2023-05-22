// Class to fetch weather data from the network.
package com.alexteodorovici.weatheralerts.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.alexteodorovici.weatheralerts.data.model.AlertResponse
import com.alexteodorovici.weatheralerts.data.model.FeatureItem
import com.alexteodorovici.weatheralerts.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class WeatherDataSource(private val apiService: ApiService) {

    suspend fun fetchWeatherAlerts(): List<FeatureItem>? = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getActiveAlerts()

            if (response.isSuccessful && response.body() != null) {
                response.body()?.features
            } else {
                null
            }
        } catch (e: HttpException) {
            // Log exception
            null
        } catch (e: IOException) {
            // Log exception
            null
        }
    }


}
