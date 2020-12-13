package com.example.weatherforecast.dagger.module

import com.example.weatherforecast.database.RoomLocation
import com.example.weatherforecast.mapper.LocationMapper
import com.example.weatherforecast.mapper.Mapper
import com.example.weatherforecast.repository.Location
import dagger.Binds
import dagger.Module

@Module
interface MapperModule {

    @Binds
    fun providesLocationMapper(locationMapper: LocationMapper): Mapper<RoomLocation, Location>
}