package com.example.weatherforecast.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomLocation::class,
        parentColumns = ["woeid"],
        childColumns = ["locationId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["locationId"])]
)
data class RoomForecast(
    @PrimaryKey
    val id: Int,
    val locationId: Int,
    val applicableDate: Long,
    val weatherStateName: String = "",
    val weatherStateAbbr: String = "",
    val minTemp: Int = 0,
    val maxTemp: Int = 0,
    val windSpeed: Float = 0F
)