package com.example.weatherforecast.mapper

import com.example.weatherforecast.database.RoomForecast
import com.example.weatherforecast.repository.Forecast
import com.example.weatherforecast.repository.ImagePath
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastMapper @Inject constructor() : Mapper<RoomForecast, Forecast> {

    override fun map(objectToMap: RoomForecast): Forecast {
        return Forecast(
            weatherStateName = objectToMap.weatherStateName,
            imagePath = ImagePath(objectToMap.weatherStateAbbr),
            minTemp = objectToMap.minTemp,
            maxTemp = objectToMap.maxTemp,
            windSpeed = objectToMap.windSpeed
        )
    }
}