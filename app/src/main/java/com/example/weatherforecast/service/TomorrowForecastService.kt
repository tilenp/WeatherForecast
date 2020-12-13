package com.example.weatherforecast.service

import com.example.weatherforecast.network.RemoteForecast
import io.reactivex.Single

interface TomorrowForecastService {
    fun getTomorrowForecast(woeid: Int): Single<RemoteForecast>
}