package com.example.weatherforecast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecast.DATABASE_NAME

@Database(
    entities = [RoomLocation::class, RoomForecast::class],
    version = 1,
    exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
    abstract fun getForecastDao(): ForecastDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): WeatherDatabase {
            return Room.databaseBuilder(context, WeatherDatabase::class.java, DATABASE_NAME)
                .createFromAsset("weather_database.db")
                .build()
        }
    }
}