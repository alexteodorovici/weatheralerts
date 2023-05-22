package com.alexteodorovici.weatheralerts.domain.usecase

import com.alexteodorovici.weatheralerts.data.model.AlertResponse
import com.alexteodorovici.weatheralerts.data.repository.WeatherRepository
import javax.inject.Inject

//this class is responsible for encapsulating the business logic related to fetching weather alerts
class FetchWeatherAlertsUseCase @Inject constructor(
    //we have a dependency on the WeatherRepository interface, which is used to access the weather data
    private val repository: WeatherRepository
) {
    suspend fun execute(): AlertResponse? {
        //suspend function that performs the actual execution of the use case
        return repository.getWeatherAlerts()
    }
}
