package com.example.weatherforecast.repository

import com.example.weatherforecast.BASE_URL
import com.example.weatherforecast.IMAGE_FORMAT
import com.example.weatherforecast.IMAGE_SIZE_64

data class ImagePath(val weatherStateAbbr: String) {

    val imageSize64 = "$BASE_URL$IMAGE_SIZE_64/$weatherStateAbbr$IMAGE_FORMAT"
}