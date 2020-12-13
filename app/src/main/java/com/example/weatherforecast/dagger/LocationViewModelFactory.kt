package com.example.weatherforecast.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.SPLIT_VIEW
import com.example.weatherforecast.repository.LocationRepository
import com.example.weatherforecast.viewModel.LocationsViewModel
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
class LocationViewModelFactory constructor(
    private val locationDepository: LocationRepository,
    @Named(SPLIT_VIEW) private val splitView: Boolean
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LocationsViewModel(locationDepository, splitView) as T
    }
}