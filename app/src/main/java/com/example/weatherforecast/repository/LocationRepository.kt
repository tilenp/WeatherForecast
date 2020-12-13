package com.example.weatherforecast.repository

import com.example.weatherforecast.database.LocationDao
import com.example.weatherforecast.database.RoomLocation
import com.example.weatherforecast.mapper.Mapper
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val locationDao: LocationDao,
    private val locationMapper: Mapper<RoomLocation, Location>
) {

    fun getLocations(): Single<List<Location>> {
        return locationDao.getLocations()
            .map { roomLocations -> roomLocations.map { locationMapper.map(it) } }
    }
}