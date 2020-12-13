package com.example.weatherforecast.dagger

import android.content.Context
import com.example.weatherforecast.dagger.module.AppModule
import com.example.weatherforecast.dagger.module.DatabaseModule
import com.example.weatherforecast.dagger.module.MapperModule
import com.example.weatherforecast.ui.locations.FragmentLocations
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        MapperModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: FragmentLocations)
}