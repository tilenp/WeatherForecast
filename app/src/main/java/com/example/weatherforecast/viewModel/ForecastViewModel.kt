package com.example.weatherforecast.viewModel

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.repository.Forecast
import com.example.weatherforecast.repository.ForecastRepository
import com.example.weatherforecast.utils.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val eventAggregator: EventAggregator,
    private val schedulerProvider: SchedulerProvider
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
        setUpForecastUpdate()
    }

    private fun setUpForecastUpdate() {
        compositeDisposable.add(
            eventAggregator.observeLocationSelected()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .switchMapCompletable { location -> forecastRepository.setUpForecastUpdate(location.woeid) }
                .subscribe({
                    System.out.println("TTT success")
                }, {
                    System.out.println("TTT error " + it.message)
                })
        )
    }

    fun getTomorrowForecast(): Observable<Forecast> {
        return eventAggregator.observeLocationSelected()
            .switchMap { location -> forecastRepository.getTomorrowForecast(location.woeid) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}