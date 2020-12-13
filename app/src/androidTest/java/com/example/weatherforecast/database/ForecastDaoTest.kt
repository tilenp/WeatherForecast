package com.example.weatherforecast.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var forecastDao: ForecastDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun setUp(
        roomLocations: List<RoomLocation>
    ) {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            database = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
                .build()

            val locationDao = database.getLocationDao()
            forecastDao = database.getForecastDao()

            locationDao.insertLocations(roomLocations).blockingAwait()
        }
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun forecast_is_inserted_correctly() {
        // arrange
        val roomLocation = RoomLocation(1, "Gothenburg", 0)
        setUp(listOf(roomLocation))

        // assert
        val roomForecast = RoomForecast(2, 1, 1)
        forecastDao.insertForecast(roomForecast)
            .test()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun forecast_is_retrieved_correctly() {
        // arrange
        val roomLocation = RoomLocation(1, "Gothenburg", 0)
        setUp(listOf(roomLocation))

        // act
        val roomForecast1 = RoomForecast(11, 1, 111)
        val roomForecast2 = RoomForecast(22, 1, 222)
        runBlocking {
            forecastDao.insertForecast(roomForecast1).blockingAwait()
            forecastDao.insertForecast(roomForecast2).blockingAwait()
        }

        // assert
        forecastDao.getForecastForDate(1, 222)
            .test()
            .assertValue(roomForecast2)
            .assertNoErrors()
            .dispose()
    }
}