package com.example.weatherforecast.repository

import com.example.weatherforecast.database.LocationDao
import com.example.weatherforecast.database.RoomLocation
import com.example.weatherforecast.mapper.Mapper
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Test

class LocationRepositoryTest {

    private val locationDao: LocationDao = mock()
    private val mapper: Mapper<RoomLocation, Location> = mock()
    private lateinit var locationRepository: LocationRepository

    private fun setUp(
        locationsSingle: Single<List<RoomLocation>> = Single.just(emptyList())
    ) {
        whenever(locationDao.getLocations()).thenReturn(locationsSingle)
        whenever(mapper.map(any())).thenReturn(Location())
        locationRepository = LocationRepository(locationDao, mapper)
    }

    @Test
    fun locations_are_retrieved_from_location_dao() {
        // arrange
        setUp()

        // act
        locationRepository.getLocations()
            .test()
            .assertComplete()
            .assertNoErrors()
            .dispose()

        // assert
        verify(locationDao, times(1)).getLocations()
    }

    @Test
    fun locations_are_mapped_using_mapper() {
        // arrange
        val roomLocations = listOf(
            RoomLocation(1, "", 0),
            RoomLocation(2, "", 0)
        )
        setUp(Single.just(roomLocations))

        // act
        locationRepository.getLocations()
            .test()
            .assertComplete()
            .assertNoErrors()
            .dispose()

        // assert
        verify(mapper, times(2)).map(any())
    }
}