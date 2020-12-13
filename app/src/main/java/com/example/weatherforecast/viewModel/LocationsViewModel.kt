package com.example.weatherforecast.viewModel

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.repository.Location
import com.example.weatherforecast.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    fun getLocations(): Single<List<Location>> {
        return locationRepository.getLocations()
    }
}