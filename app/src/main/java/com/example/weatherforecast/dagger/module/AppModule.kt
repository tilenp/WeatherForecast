package com.example.weatherforecast.dagger.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.R
import com.example.weatherforecast.SPLIT_VIEW
import com.example.weatherforecast.dagger.LocationViewModelFactory
import com.example.weatherforecast.repository.LocationRepository
import com.example.weatherforecast.utils.RuntimeSchedulerProvider
import com.example.weatherforecast.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
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
    @Named(SPLIT_VIEW)
    fun providesSplitView(context: Context): Boolean {
        return context.resources.getBoolean(R.bool.splitView)
    }

    @Singleton
    @Provides
    fun providesLocationViewModelFactory(
        locationRepository: LocationRepository,
        @Named(SPLIT_VIEW) splitView: Boolean
    ): ViewModelProvider.Factory {
        return LocationViewModelFactory(locationRepository, splitView)
    }
}