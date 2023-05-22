package com.alexteodorovici.weatheralerts.util

fun generateImageUrl(id: String): String {
    // we use a max of 1084 photos. Picsum doesn't have that many
    return "https://picsum.photos/id/${kotlin.math.abs(id.hashCode() % 1084)}/1000"
}
