package com.example.weatherforecast.mapper

import com.example.weatherforecast.BASE_URL
import com.example.weatherforecast.IMAGE_FORMAT
import com.example.weatherforecast.IMAGE_SIZE_64
import com.example.weatherforecast.database.RoomForecast
import org.junit.Assert.assertEquals
import org.junit.Test

class ForecastMapperTest {

    private val mapper = ForecastMapper()

    @Test
    fun forecast_is_mapped_correctly() {
        val weatherStateName = "stateName"
        val weatherStateAbbr = "sunny"
        val minTemp = 1
        val maxTemp = 2
        val windSpeed = 3.3f
        val roomForecast = RoomForecast(1, 1, 0L, weatherStateName, weatherStateAbbr, minTemp, maxTemp, windSpeed)

        val forecast = mapper.map(roomForecast)

        assertEquals(weatherStateName, forecast.weatherStateName)
        assertEquals("$BASE_URL$IMAGE_SIZE_64/$weatherStateAbbr$IMAGE_FORMAT", forecast.imagePath?.imageSize64)
        assertEquals(minTemp, forecast.minTemp)
        assertEquals(maxTemp, forecast.maxTemp)
        assertEquals(windSpeed, forecast.windSpeed)
    }
}