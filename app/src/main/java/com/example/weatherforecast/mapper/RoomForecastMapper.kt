package com.example.weatherforecast.mapper

import com.example.weatherforecast.database.RoomForecast
import com.example.weatherforecast.network.RemoteForecast
import com.example.weatherforecast.utils.timestampToMilliseconds
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomForecastMapper @Inject constructor() : FourMapper<RemoteForecast, Int, SimpleDateFormat, DecimalFormat, RoomForecast> {

    override fun map(
        objectToMap1: RemoteForecast,
        objectToMap2: Int,
        objectToMap3: SimpleDateFormat,
        objectToMap4: DecimalFormat
    ): RoomForecast {
        return RoomForecast(
            id = objectToMap1.id ?: 0L,
            locationId = objectToMap2,
            applicableDate = objectToMap1.applicableDate?.timestampToMilliseconds(objectToMap3) ?: 0L,
            weatherStateName = objectToMap1.weatherStateName ?: "",
            weatherStateAbbr = objectToMap1.weatherStateAbbr ?: "",
            minTemp = objectToMap1.minTemp?.let { objectToMap4.format(it).toFloat().toInt() } ?: 0,
            maxTemp =  objectToMap1.maxTemp?.let { objectToMap4.format(it).toFloat().toInt() } ?: 0,
            windSpeed = objectToMap1.windSpeed?.let { objectToMap4.format(it).toFloat() } ?: 0F
        )
    }
}