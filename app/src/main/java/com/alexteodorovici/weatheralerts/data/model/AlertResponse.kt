package com.alexteodorovici.weatheralerts.data.model

import com.squareup.moshi.Json

// Data class for the weather alerts.
data class AlertResponse(
    @Json(name = "features")
    val features: List<FeatureItem>,
)

data class FeatureItem(
    @Json(name = "properties")
    val properties: AlertProperties?,
)

data class AlertProperties(
    @Json(name = "id")
    val eventId: String,

    @Json(name = "event")
    val eventName: String?,

    @Json(name = "effective")
    val startDate: String?,

    @Json(name = "ends")
    val endDate: String?,

    @Json(name = "senderName")
    val source: String?,
)