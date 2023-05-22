// Class to fetch image data from the network.
package com.alexteodorovici.weatheralerts.data.network

import okhttp3.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ImageDataSource(private val client: OkHttpClient) {

    suspend fun fetchImage(imageUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(imageUrl)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            response.body?.byteStream()?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    }
}
