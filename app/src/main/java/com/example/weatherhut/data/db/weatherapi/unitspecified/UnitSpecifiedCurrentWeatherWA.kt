package com.example.weatherhut.data.db.weatherapi.unitspecified

import androidx.room.Embedded
import com.example.weatherhut.data.db.weatherapi.Condition

interface UnitSpecifiedCurrentWeatherWA {
    val cloud: Int
    val condition: Condition
    val feelsLikeTemperature: Double
    val gust: Double
    val humidity: Int
    val isDay: Int
    val lastUpdated: String
    val lastUpdatedEpoch: Int
    val precipitation: Double
    val pressure: Double
    val temperature: Double
    val uv: Double
    val visibility: Double
    val windDegree: Int
    val windDir: String
    val wind: Double
}