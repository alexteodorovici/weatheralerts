package com.alexteodorovici.weatheralerts.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexteodorovici.weatheralerts.domain.model.Alert
import com.alexteodorovici.weatheralerts.util.generateImageUrl

@Composable
fun WeatherAlertList(alerts: List<Alert>) {
    //we use LazyColumn to iterate and create our alert cards in a scrollable list
    LazyColumn {
        items(alerts) { alert ->
            Box(modifier = Modifier.padding(8.dp)) {
                WeatherAlertCard(alert, generateImageUrl(alert.id))
            }
        }
    }
}

@Preview
@Composable
fun PreviewWeatherAlertList() {
    val sampleAlerts = listOf(
        Alert("1", "Alert1", "Start1", "End1", "Source1", "Duration1"),
        Alert("1", "Alert2", "Start2", "End2", "Source2", "Duration2"),
    )
    WeatherAlertList(sampleAlerts)
}
