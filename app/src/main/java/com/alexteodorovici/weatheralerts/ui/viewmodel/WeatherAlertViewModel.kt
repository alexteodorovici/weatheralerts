package com.alexteodorovici.weatheralerts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexteodorovici.weatheralerts.domain.mapper.AlertMapper
import com.alexteodorovici.weatheralerts.domain.model.Alert
import com.alexteodorovici.weatheralerts.domain.usecase.FetchWeatherAlertsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherAlertViewModel @Inject constructor(
    private val fetchWeatherAlertsUseCase: FetchWeatherAlertsUseCase,
    private val alertMapper: AlertMapper
) : ViewModel() {
    //we can change this one to trigger recomposition
    private val _weatherAlerts = MutableStateFlow<List<Alert>>(emptyList())

    //compose observes this one and triggers recomposition
    val weatherAlerts: StateFlow<List<Alert>> = _weatherAlerts

    init {
        //get the alerts when this viewModel is created.
        fetchWeatherAlerts()
    }

    private fun fetchWeatherAlerts() {
        //tie this network call to the viewModel scope lifecycle to prevent memory leaks.
        viewModelScope.launch {
            //ask our use case for alerts
            val alertResponse = fetchWeatherAlertsUseCase.execute()

            //update the alerts and trigger recomposition
            _weatherAlerts.value = alertMapper.mapFromNetworkModel(alertResponse)
        }
    }
}