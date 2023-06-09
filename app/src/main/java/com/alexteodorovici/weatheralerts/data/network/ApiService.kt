package com.alexteodorovici.weatheralerts.data.network

import com.alexteodorovici.weatheralerts.data.model.AlertResponse
import retrofit2.Response
import retrofit2.http.GET

//Retrofit interface for the API
interface ApiService {
    @GET("alerts/active?status=actual&message_type=alert")
    suspend fun getActiveAlerts(): Response<AlertResponse>
}
