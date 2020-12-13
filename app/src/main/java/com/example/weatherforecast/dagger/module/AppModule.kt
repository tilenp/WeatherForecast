package com.example.weatherforecast.dagger.module

import android.content.Context
import com.example.weatherforecast.R
import com.example.weatherforecast.SPLIT_VIEW
import com.example.weatherforecast.UPDATE_INTERVAL_IN_HOURS
import com.example.weatherforecast.utils.RuntimeSchedulerProvider
import com.example.weatherforecast.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import java.util.*
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
    @Named(UPDATE_INTERVAL_IN_HOURS)
    fun providesUpdateInterval(): Int {
        return 12
    }

    @Singleton
    @Provides
    fun providesLocale(): Locale {
        return Locale.getDefault()
    }
}