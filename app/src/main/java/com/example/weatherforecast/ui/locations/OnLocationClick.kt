package com.example.weatherforecast.ui.locations

import com.example.weatherforecast.repository.Location

interface OnLocationClick {
    fun onLocationClick(location: Location)
}