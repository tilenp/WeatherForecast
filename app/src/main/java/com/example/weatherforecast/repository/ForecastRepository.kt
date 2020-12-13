package com.example.weatherforecast.repository

import com.example.weatherforecast.DATE_PATTERN
import com.example.weatherforecast.DECIMAL_FORMAT
import com.example.weatherforecast.UPDATE_INTERVAL_IN_HOURS
import com.example.weatherforecast.database.ForecastDao
import com.example.weatherforecast.database.RoomForecast
import com.example.weatherforecast.mapper.Mapper
import com.example.weatherforecast.mapper.FourMapper
import com.example.weatherforecast.network.RemoteForecast
import com.example.weatherforecast.service.TomorrowForecastService
import com.example.weatherforecast.utils.tomorrowMills
import io.reactivex.Completable
import io.reactivex.Observable
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ForecastRepository @Inject constructor(
    private val forecastService: TomorrowForecastService,
    private val forecastDao: ForecastDao,
    private val roomForecastMapper: FourMapper<RemoteForecast, Int, SimpleDateFormat, DecimalFormat, RoomForecast>,
    private val forecastMapper: Mapper<RoomForecast, Forecast>,
    @Named(UPDATE_INTERVAL_IN_HOURS) private val updateIntervalInHours: Int,
    private val locale: Locale
) {

    fun setUpForecastUpdate(woeId: Int): Completable {
        val dateFormatter = SimpleDateFormat(DATE_PATTERN, locale)
        val decimalFormat = DecimalFormat(DECIMAL_FORMAT)
        return Observable.merge(
            forecastDao.getForecastUpdateTimestamp(woeId)
                .filter { timestamp -> timestamp + updateIntervalInHours * 60 * 60 * 1000 < System.currentTimeMillis() }
                .toObservable(),
            Observable.interval(updateIntervalInHours.toLong(), updateIntervalInHours.toLong(), TimeUnit.HOURS)
        ).flatMapCompletable { _ ->
            forecastService.getTomorrowForecast(woeId)
                .map { remoteForecast -> roomForecastMapper.map(remoteForecast, woeId, dateFormatter, decimalFormat) }
                .flatMapCompletable { roomForecast -> forecastDao.insertForecast(roomForecast) }
                .andThen { forecastDao.setForecastUpdateTimestamp(System.currentTimeMillis(), woeId) }
        }
    }

    fun getTomorrowForecast(woeId: Int): Observable<Forecast> {
        return forecastDao.getForecastForDate(woeId, tomorrowMills())
            .map { roomForecast -> forecastMapper.map(roomForecast) }
    }
}