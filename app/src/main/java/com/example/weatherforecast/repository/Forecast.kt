package com.example.weatherforecast.repository

data class Forecast(
    val title: String = "",
    val weatherStateName: String = "",
    val imagePath: ImagePath? = null,
    val minTemp: Int = 0,
    val maxTemp: Int = 0,
    val windSpeed: Float = 0F
)