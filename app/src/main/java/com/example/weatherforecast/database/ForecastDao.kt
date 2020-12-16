package com.example.weatherforecast.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ForecastDao {

    @Query("SELECT RoomLocation.forecastUpdateTimestamp FROM RoomLocation WHERE RoomLocation.woeid = :woeId")
    fun getForecastUpdateTimestamp(woeId: Int): Single<Long>

    @Query("UPDATE RoomLocation SET forecastUpdateTimestamp = :timestamp WHERE RoomLocation.woeid = :woeId")
    fun setForecastUpdateTimestamp(timestamp: Long, woeId: Int): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(roomForecasts: RoomForecast): Completable

    @Query("SELECT RoomLocation.title FROM RoomLocation WHERE RoomLocation.woeid = :woeId")
    fun getLocationTitle(woeId: Int): Single<String>

    @Query("SELECT * FROM RoomForecast WHERE RoomForecast.locationId = :woeId AND RoomForecast.applicableDate = :applicableDate LIMIT 1")
    fun getForecastForDate(woeId: Int, applicableDate: Long): Observable<RoomForecast>
}