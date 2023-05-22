package com.alexteodorovici.weatheralerts.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alexteodorovici.weatheralerts.R
import com.alexteodorovici.weatheralerts.domain.model.Alert

@Composable
fun WeatherAlertCard(alert: Alert, imageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                // asynchronously load the image
                painter = rememberAsyncImagePainter(
                    //construct the image request
                    ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_foreground)
                        error(R.drawable.ic_launcher_foreground)
                    }).build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .weight(1f),
                contentScale = ContentScale.Inside
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(2f)
            ) {
                Text(
                    text = "Event: ${alert.eventName}",
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = "Start date: ${alert.startDate}")
                Text(text = "End date: ${alert.endDate ?: "N/A"}")
                Text(text = "Source: ${alert.source}")
                Text(text = "Duration: ${alert.duration}")
            }
        }
    }
}
