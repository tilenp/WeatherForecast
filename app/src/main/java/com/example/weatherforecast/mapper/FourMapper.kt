package com.example.weatherforecast.mapper

interface FourMapper<A, B, C, D, F> {
    fun map(objectToMap1: A, objectToMap2: B, objectToMap3: C, objectToMap4: D): F
}