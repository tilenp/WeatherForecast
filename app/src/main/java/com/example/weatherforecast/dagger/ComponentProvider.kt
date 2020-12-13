package com.example.weatherforecast.dagger

interface ComponentProvider {
    fun provideAppComponent(): AppComponent
}