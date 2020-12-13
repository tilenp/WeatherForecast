package com.example.weatherforecast.dagger

import android.content.Context
import com.example.weatherforecast.dagger.module.*
import com.example.weatherforecast.ui.forecast.FragmentForecast
import com.example.weatherforecast.ui.locations.FragmentLocations
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        ServiceModule::class,
        DatabaseModule::class,
        MapperModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: FragmentLocations)
    fun inject(fragment: FragmentForecast)
}