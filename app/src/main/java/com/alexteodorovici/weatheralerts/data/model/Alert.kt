// Data class for the weather alerts.
package com.alexteodorovici.weatheralerts.data.model

import com.google.gson.annotations.SerializedName

data class AlertResponse(
    @SerializedName("features")
    val features: List<FeatureItem>,
)

data class FeatureItem(
    @SerializedName("properties")
    val properties: AlertProperties,
)

data class AlertProperties(
    @SerializedName("event")
    val eventName: String,

    @SerializedName("effective")
    val startDate: String,

    @SerializedName("ends")
    val endDate: String?,

    @SerializedName("senderName")
    val source: String,
)
