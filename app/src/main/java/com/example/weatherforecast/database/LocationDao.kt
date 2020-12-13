package com.example.weatherforecast.database

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface LocationDao {

    @Query("SELECT * FROM RoomLocation ORDER BY RoomLocation.title ASC")
    fun getLocations(): Single<List<RoomLocation>>
}