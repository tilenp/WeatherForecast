package com.example.weatherforecast.viewModel

import com.example.weatherforecast.repository.Location
import com.example.weatherforecast.repository.LocationRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class LocationsViewModelTest {

    private val locationRepository: LocationRepository = mock()
    private lateinit var locationViewModel: LocationsViewModel

    private fun setUp(
        locationsSingle: Single<List<Location>> = Single.just(emptyList())
    ) {
        whenever(locationRepository.getLocations()).thenReturn(locationsSingle)
        locationViewModel = LocationsViewModel(locationRepository)
    }

    @Test
    fun locations_are_retrieved_from_location_repository() {
        setUp()

        // act
        locationViewModel.getLocations()
            .test()
            .assertComplete()
            .assertNoErrors()
            .dispose()

        // assert
        verify(locationRepository, times(1)).getLocations()
    }
}