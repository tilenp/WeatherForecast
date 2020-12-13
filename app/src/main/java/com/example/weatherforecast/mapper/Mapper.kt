package com.example.weatherforecast.mapper

interface Mapper<A, B> {
    fun map(objectToMap: A): B
}