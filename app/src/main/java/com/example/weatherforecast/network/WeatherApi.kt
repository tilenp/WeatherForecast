package com.example.weatherforecast.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("/api/location/{woeid}")
    fun getForecast(
        @Path("woeid") woeid: Int
    ): Single<RemoteWeatherData>
}