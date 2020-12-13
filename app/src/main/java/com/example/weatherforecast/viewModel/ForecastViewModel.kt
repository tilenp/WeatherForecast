package com.example.weatherforecast.viewModel

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.R
import com.example.weatherforecast.repository.Forecast
import com.example.weatherforecast.repository.ForecastRepository
import com.example.weatherforecast.utils.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository,
    private val eventAggregator: EventAggregator,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val uiStateSubject = BehaviorSubject.create<UIState>()
    private val compositeDisposable = CompositeDisposable()

    private fun setUpForecastUpdate() {
        compositeDisposable.add(
            eventAggregator.observeLocationSelected()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .switchMapCompletable { location ->
                    forecastRepository.setUpForecastUpdate(location.woeid)
                        .doOnSubscribe { uiStateSubject.onNext(UIState.Loading) }
                        .doOnError { uiStateSubject.onNext(UIState.Error(R.string.update_forecast_error)) }
                        .onErrorComplete()
                }
                .subscribe({
                    System.out.println("TTT success")
                }, {
                    System.out.println("TTT error " + it.message)
                })
        )
    }

    fun start() {
        compositeDisposable.clear()
        setUpForecastUpdate()
    }

    fun stop() {
        compositeDisposable.clear()
    }

    fun getUIState(): Observable<UIState> {
        return uiStateSubject
            .distinctUntilChanged()
    }

    fun getTomorrowForecast(): Observable<Forecast> {
        return eventAggregator.observeLocationSelected()
            .switchMap { location ->
                forecastRepository.getTomorrowForecast(location.woeid)
                    .doOnNext { uiStateSubject.onNext(UIState.Success) }
                    .doOnError { uiStateSubject.onNext(UIState.Error(R.string.database_error)) }
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}