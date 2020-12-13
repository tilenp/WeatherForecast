package com.example.weatherforecast.dagger.module

import android.content.Context
import com.example.weatherforecast.database.ForecastDao
import com.example.weatherforecast.database.LocationDao
import com.example.weatherforecast.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesWeatherDatabase(context: Context): WeatherDatabase {
        return WeatherDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun providesLocationDao(database: WeatherDatabase): LocationDao {
        return database.getLocationDao()
    }

    @Singleton
    @Provides
    fun providesForecastDao(database: WeatherDatabase): ForecastDao {
        return database.getForecastDao()
    }
}