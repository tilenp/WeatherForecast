package com.example.weatherforecast.network

data class RemoteWeatherData (
    val consolidatedWeather: List<RemoteForecast?>? = null
)