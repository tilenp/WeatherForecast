package com.example.weatherforecast.mapper

interface TwoMapper<A, B, C> {
    fun map(objectToMap1: A, objectToMap2: B): C
}