package com.alexteodorovici.weatheralerts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexteodorovici.weatheralerts.domain.model.Alert
import com.alexteodorovici.weatheralerts.ui.composables.WeatherAlertList
import com.alexteodorovici.weatheralerts.ui.theme.WeatherAlertsTheme
import com.alexteodorovici.weatheralerts.ui.viewmodel.WeatherAlertViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // initialize our viewModel lazily - when we first access it.
    private val viewModel by viewModels<WeatherAlertViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAlertsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Asynchronously observe the weatherAlerts changes and trigger recomposition
                    val weatherAlerts by viewModel.weatherAlerts.collectAsState()

                    // Display the WeatherAlertList
                    WeatherAlertList(alerts = weatherAlerts)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAlertListPreview() {
    WeatherAlertsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val sampleAlerts = listOf(
                Alert("1", "Tornado Warning", "2023-05-21T20:00:00Z", "2023-05-21T21:00:00Z", "National Weather Service", "1 hour"),
                Alert("1", "Flood Warning", "2023-05-21T19:00:00Z", "2023-05-21T20:00:00Z", "National Weather Service", "1 hour")
            )
            WeatherAlertList(alerts = sampleAlerts)
        }
    }
}
