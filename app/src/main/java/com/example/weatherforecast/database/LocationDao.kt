package com.example.weatherforecast.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocations(roomLocations: List<RoomLocation>): Completable

    @Query("SELECT * FROM RoomLocation ORDER BY RoomLocation.title ASC")
    fun getLocations(): Single<List<RoomLocation>>
}