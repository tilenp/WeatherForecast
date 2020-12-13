package com.example.weatherforecast.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.dagger.LocationViewModelFactory
import com.example.weatherforecast.repository.LocationRepository
import com.example.weatherforecast.utils.RuntimeSchedulerProvider
import com.example.weatherforecast.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesSchedulerProvider(): SchedulerProvider {
        return RuntimeSchedulerProvider()
    }

    @Singleton
    @Provides
    fun providesLocationViewModelFactory(
        locationRepository: LocationRepository
    ): ViewModelProvider.Factory {
        return LocationViewModelFactory(locationRepository)
    }
}