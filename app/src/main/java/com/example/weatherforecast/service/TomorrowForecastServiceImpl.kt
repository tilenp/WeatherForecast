package com.example.weatherforecast.service

import com.example.weatherforecast.DATE_PATTERN
import com.example.weatherforecast.network.RemoteForecast
import com.example.weatherforecast.network.WeatherApi
import com.example.weatherforecast.utils.tomorrowMills
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TomorrowForecastServiceImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val locale: Locale
) : TomorrowForecastService {

    override fun getTomorrowForecast(woeid: Int): Single<RemoteForecast> {
        return weatherApi.getForecast(woeid)
            .map { remoteWeatherData -> getTomorrowsForecast(remoteWeatherData.consolidatedWeather) }
    }

    private fun getTomorrowsForecast(remoteForecasts: List<RemoteForecast?>?): RemoteForecast? {
        val dateFormatter = SimpleDateFormat(DATE_PATTERN, locale)
        val tomorrowsDate = dateFormatter.format(tomorrowMills())
        remoteForecasts?.forEach { remoteForecast ->
            remoteForecast?.let {
                if (isForecastValid(it) && it.applicableDate?.equals(tomorrowsDate) == true) {
                    return it
                }
            }
        }
        return null
    }

    private fun isForecastValid(remoteForecast: RemoteForecast): Boolean {
        return remoteForecast.id != null &&
                remoteForecast.applicableDate != null &&
                remoteForecast.weatherStateName != null &&
                remoteForecast.weatherStateAbbr != null &&
                remoteForecast.minTemp != null &&
                remoteForecast.maxTemp != null &&
                remoteForecast.windSpeed != null
    }
}