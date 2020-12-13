package com.example.weatherforecast.viewModel

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.R
import com.example.weatherforecast.SPLIT_VIEW
import com.example.weatherforecast.repository.Location
import com.example.weatherforecast.repository.LocationRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

class LocationsViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    @Named(SPLIT_VIEW) private val splitView: Boolean
) : ViewModel() {

    private val navigationSubject = PublishSubject.create<Int>()

    fun onLocationClick() {
        if (!splitView) {
            navigationSubject.onNext(R.id.action_fragmentLocations_to_fragmentForecast)
        }
    }

    fun getLocations(): Single<List<Location>> {
        return locationRepository.getLocations()
    }

    fun getNavigation(): Observable<Int> {
        return navigationSubject
    }
}