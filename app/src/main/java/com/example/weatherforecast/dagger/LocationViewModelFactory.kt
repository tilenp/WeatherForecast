package com.example.weatherforecast.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.repository.LocationRepository
import com.example.weatherforecast.viewModel.LocationsViewModel

@Suppress("UNCHECKED_CAST")
class LocationViewModelFactory constructor(
    private val locationDepository: LocationRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationsViewModel(locationDepository) as T
    }
}