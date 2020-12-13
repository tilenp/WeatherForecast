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
class LocationDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var locationDao: LocationDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun setUp(roomLocations: List<RoomLocation>) {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            database = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
                .build()

            locationDao = database.getLocationDao()
            locationDao.insertLocations(roomLocations).blockingAwait()
        }
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun locations_are_retrieved_in_alphabetical_order() {
        // arrange
        val locations = listOf(
            RoomLocation(1, "Gothenburg", 0),
            RoomLocation(2, "Stockholm", 0),
            RoomLocation(3, "Mountain View", 0)
        )
        setUp(locations)

        // assert
        locationDao.getLocations()
            .map { roomLocations -> roomLocations.map { it.title } }
            .test()
            .assertValue(listOf("Gothenburg", "Mountain View", "Stockholm"))
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }
}