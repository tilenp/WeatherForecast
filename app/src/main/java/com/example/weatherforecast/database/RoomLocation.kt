package com.example.weatherforecast.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomLocation (
    @PrimaryKey
    val woeid: Int,
    val title: String,
    val forecastUpdateTimestamp: Long
)