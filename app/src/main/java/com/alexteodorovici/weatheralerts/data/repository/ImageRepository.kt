// Repository for image data.

package com.alexteodorovici.weatheralerts.data.repository

import android.graphics.Bitmap
import com.alexteodorovici.weatheralerts.data.network.ImageDataSource
import javax.inject.Inject

class ImageRepository @Inject constructor(private val dataSource: ImageDataSource) {

    suspend fun fetchImage(url: String): Bitmap? {
        return dataSource.fetchImage(url)
    }
}
