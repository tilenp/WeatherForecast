package com.example.weatherforecast.mapper

import com.example.weatherforecast.database.RoomLocation
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationMapperTest {

    private val mapper = LocationMapper()

    @Test
    fun location_is_mapped_correctly() {
        val woeid = 1
        val title = "Stockholm"
        val forecastUpdateTimestamp = 0L
        val roomLocation = RoomLocation(woeid, title, forecastUpdateTimestamp)

        val location = mapper.map(roomLocation)

        assertEquals(woeid, location.woeid)
        assertEquals(title, location.title)
    }
}