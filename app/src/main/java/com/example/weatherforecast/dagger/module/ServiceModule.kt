package com.example.weatherforecast.dagger.module

import com.example.weatherforecast.service.TomorrowForecastService
import com.example.weatherforecast.service.TomorrowForecastServiceImpl
import dagger.Binds
import dagger.Module

@Module
interface ServiceModule {

    @Binds
    fun providesTomorrowForecastService(tomorrowForecastServiceImpl: TomorrowForecastServiceImpl): TomorrowForecastService
}