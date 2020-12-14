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

    private val uiStateSubject = BehaviorSubject.createDefault<UIState>(UIState.Default).toSerialized()
    private val compositeDisposable = CompositeDisposable()

    private fun setUpForecastUpdate() {
        compositeDisposable.add(
            eventAggregator.observeLocationSelected()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .switchMapCompletable { location ->
                    forecastRepository.setUpForecastUpdate(location.woeid)
                        .doOnSubscribe { uiStateSubject.onNext(UIState.StartLoading) }
                        .doOnComplete { uiStateSubject.onNext(UIState.UpdateSuccess) }
                        .doOnError { uiStateSubject.onNext(UIState.Error(R.string.update_forecast_error)) }
                        .onErrorComplete()
                }
                .subscribe({}, {})
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
            .scan { oldState: UIState, newState: UIState ->
                when {
                    newState is UIState.StartLoading && oldState is UIState.Default -> UIState.Loading.FromDefault
                    newState is UIState.StartLoading && oldState is UIState.UpdateSuccess -> UIState.Loading.FromUpdateSuccess
                    newState is UIState.StartLoading && oldState is UIState.QuerySuccess -> UIState.Loading.FromQuerySuccess

                    newState is UIState.UpdateSuccess && oldState is UIState.Loading.FromDefault -> UIState.Default
                    newState is UIState.UpdateSuccess && oldState is UIState.Loading.FromUpdateSuccess -> UIState.Default
                    newState is UIState.UpdateSuccess && oldState is UIState.Loading.FromQuerySuccess -> UIState.QuerySuccess
                    newState is UIState.UpdateSuccess && oldState is UIState.Loading.FromError -> UIState.Error(oldState.stringId)

                    else -> newState
                }
            }
    }

    fun getTomorrowForecast(): Observable<Forecast> {
        return eventAggregator.observeLocationSelected()
            .switchMap { location ->
                forecastRepository.getTomorrowForecast(location.woeid)
                    .doOnSubscribe { uiStateSubject.onNext(UIState.StartLoading) }
                    .doOnNext { uiStateSubject.onNext(UIState.QuerySuccess) }
                    .doOnError { uiStateSubject.onNext(UIState.Error(R.string.database_error)) }
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}