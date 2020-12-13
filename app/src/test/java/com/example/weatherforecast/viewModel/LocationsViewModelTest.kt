package com.example.weatherforecast.viewModel

import com.example.weatherforecast.R
import com.example.weatherforecast.repository.Location
import com.example.weatherforecast.repository.LocationRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Test

class LocationsViewModelTest {

    private val locationRepository: LocationRepository = mock()
    private val eventAggregator: EventAggregator = mock()
    private lateinit var locationViewModel: LocationsViewModel

    private fun setUp(
        splitView: Boolean = false,
        locationsSingle: Single<List<Location>> = Single.just(emptyList())
    ) {
        whenever(locationRepository.getLocations()).thenReturn(locationsSingle)
        locationViewModel = LocationsViewModel(locationRepository, splitView, eventAggregator)
    }

    @Test
    fun locations_are_retrieved_from_location_repository() {
        // arrange
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

    @Test
    fun when_view_is_not_split_location_click_triggers_navigation() {
        // arrange
        setUp(
            splitView = false
        )
        val navigationObserver = locationViewModel.getNavigation()
        val testObserver = navigationObserver.test()

        // act
        locationViewModel.onLocationClick(Location())

        // assert
        testObserver
            .assertValue(R.id.action_fragmentLocations_to_fragmentForecast)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun when_view_is_split_navigation_is_not_triggered() {
        // arrange
        setUp(
            splitView = true
        )
        val navigationObserver = locationViewModel.getNavigation()
        val testObserver = navigationObserver.test()

        // act
        locationViewModel.onLocationClick(Location())

        // assert
        testObserver
            .assertNoValues()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun when_location_is_selected_event_aggregator_is_triggered() {
        // arrange
        setUp(
            splitView = true
        )

        // act
        locationViewModel.onLocationClick(Location())

        // assert
        verify(eventAggregator, times(1)).locationSelected(any())
    }
}