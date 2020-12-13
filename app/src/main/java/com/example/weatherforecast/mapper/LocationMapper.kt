package com.example.weatherforecast.mapper

import com.example.weatherforecast.database.RoomLocation
import com.example.weatherforecast.repository.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationMapper @Inject constructor() : Mapper<RoomLocation, Location> {

    override fun map(objectToMap: RoomLocation): Location {
        return Location(
            woeid = objectToMap.woeid,
            title = objectToMap.title
        )
    }
}