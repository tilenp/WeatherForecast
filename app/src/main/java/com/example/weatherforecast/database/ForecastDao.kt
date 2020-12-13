package com.example.weatherforecast.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(roomForecasts: RoomForecast): Completable

    @Query("SELECT * FROM RoomForecast WHERE RoomForecast.locationId = :locationId")
    fun getForecast(locationId: Int): Observable<RoomForecast>
}