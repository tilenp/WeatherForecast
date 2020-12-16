package com.example.weatherforecast.mapper

import com.example.weatherforecast.database.RoomForecast
import com.example.weatherforecast.repository.Forecast
import com.example.weatherforecast.repository.ImagePath
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastMapper @Inject constructor() : TwoMapper<RoomForecast, String, Forecast> {

    override fun map(objectToMap1: RoomForecast, objectToMap2: String): Forecast {
        return Forecast(
            title = objectToMap2,
            weatherStateName = objectToMap1.weatherStateName,
            imagePath = ImagePath(objectToMap1.weatherStateAbbr),
            minTemp = objectToMap1.minTemp,
            maxTemp = objectToMap1.maxTemp,
            windSpeed = objectToMap1.windSpeed
        )
    }
}