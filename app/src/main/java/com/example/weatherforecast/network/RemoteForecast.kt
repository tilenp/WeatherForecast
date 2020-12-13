package com.example.weatherforecast.network

data class RemoteForecast(
    val id: Long? = null,
    val applicableDate: String? = null,
    val weatherStateName: String? = null,
    val weatherStateAbbr: String? = null,
    val minTemp: Double? = null,
    val maxTemp: Double? = null,
    val windSpeed: Double? = null
)