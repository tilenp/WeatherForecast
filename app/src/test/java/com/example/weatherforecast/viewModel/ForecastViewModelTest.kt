package com.example.weatherforecast.viewModel

import com.example.weatherforecast.R
import com.example.weatherforecast.repository.Forecast
import com.example.weatherforecast.repository.ForecastRepository
import com.example.weatherforecast.repository.Location
import com.example.weatherforecast.utils.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test

class ForecastViewModelTest {

    private val forecastRepository: ForecastRepository = mock()
    private val eventAggregator: EventAggregator = mock()
    private lateinit var viewModel: ForecastViewModel

    private fun setUp(
        selectedLocationObservable: Observable<Location> = Observable.never(),
        updateForecastCompletable: Completable = Completable.never(),
        forecastObservable: Observable<Forecast> = Observable.never()
    ) {
        whenever(eventAggregator.observeLocationSelected()).thenReturn(selectedLocationObservable)
        whenever(forecastRepository.setUpForecastUpdate(any())).thenReturn(updateForecastCompletable)
        whenever(forecastRepository.getTomorrowForecast(any())).thenReturn(forecastObservable)
        viewModel = ForecastViewModel(forecastRepository, eventAggregator, TestSchedulerProvider())
    }

    @Test
    fun when_update_is_done_from_default_state_flow_is_correct() {
        // arrange
        setUp(
            selectedLocationObservable = Observable.just(Location()),
            updateForecastCompletable = Completable.complete()
        )

        val testObserver = viewModel.getUIState().test()

        // act
        viewModel.start()

        testObserver
            .assertValueCount(3)
            .assertValueAt(0, UIState.Default)
            .assertValueAt(1, UIState.Loading.FromDefault)
            .assertValueAt(2, UIState.Default)
            .dispose()
    }

    @Test
    fun when_update_is_done_from_query_success_state_flow_is_correct() {
        // arrange
        setUp(
            selectedLocationObservable = Observable.just(Location()),
            updateForecastCompletable = Completable.complete(),
            forecastObservable = Observable.just(Forecast())
        )

        val testObserver = viewModel.getUIState().test()

        // act
        // act
        viewModel.getTomorrowForecast()
            .test()
            .dispose()

        viewModel.start()

        testObserver
            .assertValueCount(5)
            .assertValueAt(0, UIState.Default)
            .assertValueAt(1, UIState.Loading.FromDefault)
            .assertValueAt(2, UIState.QuerySuccess)
            .assertValueAt(3, UIState.Loading.FromQuerySuccess)
            .assertValueAt(4, UIState.QuerySuccess)
            .dispose()
    }

    @Test
    fun when_update_is_done_from_error_state_flow_is_correct() {
        // arrange
        setUp(
            selectedLocationObservable = Observable.just(Location()),
            updateForecastCompletable = Completable.complete(),
            forecastObservable = Observable.error(Throwable())
        )
        val testObserver = viewModel.getUIState().test()

        // act
        viewModel.getTomorrowForecast()
            .test()
            .dispose()

        viewModel.start()

        // assert
        testObserver
            .assertValueCount(5)
            .assertValueAt(0, UIState.Default)
            .assertValueAt(1, UIState.Loading.FromDefault)
            .assertValueAt(2, UIState.Error(R.string.database_error))
            .assertValueAt(3, UIState.Loading.FromError(R.string.database_error))
            .assertValueAt(4, UIState.Default)
            .dispose()
    }

    @Test
    fun when_forecast_update_fails_error_state_is_set() {
        // arrange
        setUp(
            selectedLocationObservable = Observable.just(Location()),
            updateForecastCompletable = Completable.error(Throwable())
        )

        // act
        viewModel.start()

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.Error(R.string.update_forecast_error))
            .dispose()

        viewModel.stop()
    }

    @Test
    fun when_forecast_is_emitted_success_state_is_set() {
        // arrange
        setUp(
            selectedLocationObservable = Observable.just(Location()),
            forecastObservable = Observable.just(Forecast())
        )

        // act
        viewModel.getTomorrowForecast()
            .test()
            .dispose()

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.QuerySuccess)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun when_database_query_fails_error_state_is_set() {
        // arrange
        setUp(
            selectedLocationObservable = Observable.just(Location()),
            forecastObservable = Observable.error(Throwable())
        )

        // act
        viewModel.getTomorrowForecast()
            .test()
            .dispose()

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.Error(R.string.database_error))
            .assertNoErrors()
            .dispose()
    }
}