package com.alexteodorovici.weatheralerts.domain.model

//we map the network models to this nice Alert class to be used in our composable
data class Alert(
    val id: String,
    val eventName: String,
    val startDate: String,
    val endDate: String?,
    val source: String,
    val duration: String
)
