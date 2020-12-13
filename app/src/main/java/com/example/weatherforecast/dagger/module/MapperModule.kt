package com.example.weatherforecast.dagger.module

import com.example.weatherforecast.database.RoomForecast
import com.example.weatherforecast.database.RoomLocation
import com.example.weatherforecast.mapper.*
import com.example.weatherforecast.network.RemoteForecast
import com.example.weatherforecast.repository.Forecast
import com.example.weatherforecast.repository.Location
import dagger.Binds
import dagger.Module
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@Module
interface MapperModule {

    @Binds
    fun providesLocationMapper(locationMapper: LocationMapper): Mapper<RoomLocation, Location>

    @Binds
    fun providesRoomForecastMapper(roomForecastMapper: RoomForecastMapper): FourMapper<RemoteForecast, Int, SimpleDateFormat, DecimalFormat, RoomForecast>

    @Binds
    fun providesForecastMapper(forecastMapper: ForecastMapper): Mapper<RoomForecast, Forecast>
}