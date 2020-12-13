package com.example.weatherforecast.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.dagger.MyViewModelFactory
import com.example.weatherforecast.dagger.ViewModelKey
import com.example.weatherforecast.viewModel.ForecastViewModel
import com.example.weatherforecast.viewModel.LocationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Singleton
    @Binds
    fun bindViewModelFactory(viewModelFactory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    fun bindLocationsViewModel(viewModel: LocationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    fun bindForecastViewModel(viewModel: ForecastViewModel): ViewModel
}