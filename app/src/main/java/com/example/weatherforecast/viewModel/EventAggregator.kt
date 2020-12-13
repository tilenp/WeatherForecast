package com.example.weatherforecast.viewModel

import com.example.weatherforecast.repository.Location
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventAggregator @Inject constructor() {

    private val locationSubject = BehaviorSubject.create<Location>()

    fun locationSelected(location: Location) {
        locationSubject.onNext(location)
    }

    fun observeLocationSelected(): Observable<Location> {
        return locationSubject
    }
}