package com.alexteodorovici.weatheralerts.domain.mapper

import com.alexteodorovici.weatheralerts.data.model.AlertResponse
import com.alexteodorovici.weatheralerts.domain.model.Alert
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AlertMapper {
    //we use the required formatter from the api.weather.gov website
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    // convert network models to domain models
    fun mapFromNetworkModel(alertResponse: AlertResponse?): List<Alert> {
        return alertResponse?.features?.map { featureItem ->
            val properties = featureItem.properties
            if (properties != null) {
                val startDate = properties.startDate?.let {
                    LocalDateTime.parse(it, dateTimeFormatter)
                }
                val endDate = properties.endDate?.let {
                    LocalDateTime.parse(it, dateTimeFormatter)
                }

                // Define the format for nice display of date/time in our composable
                val displayDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

                Alert(
                    id = properties.eventId,
                    eventName = properties.eventName ?: "N/A",
                    startDate = startDate?.format(displayDateFormat) ?: "N/A",
                    endDate = endDate?.format(displayDateFormat) ?: "N/A",
                    source = properties.source ?: "N/A",
                    duration = calculateDuration(startDate, endDate)
                )
            } else {
                return emptyList()
            }
        } ?: emptyList()
    }

    private fun calculateDuration(startDate: LocalDateTime?, endDate: LocalDateTime?): String {
        //we nicely show the day, hour and minutes as they are available.
        if (startDate != null) {
            if (endDate != null) {
                val durationMinutes = ChronoUnit.MINUTES.between(startDate, endDate)
                val durationHours = durationMinutes / 60
                val durationDays = durationHours / 24

                return when {
                    durationDays > 0 -> {
                        val remainingHours = durationHours % 24
                        val remainingMinutes = durationMinutes % 60
                        "$durationDays days, $remainingHours hours, $remainingMinutes minutes"
                    }

                    durationHours > 0 -> {
                        val remainingMinutes = durationMinutes % 60
                        "$durationHours hours, $remainingMinutes minutes"
                    }

                    else -> "$durationMinutes minutes"
                }
            } else {
                return "Ongoing"
            }
        }
        return "N/A"
    }
}
