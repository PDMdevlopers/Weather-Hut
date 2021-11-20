package com.example.weatherhut.data.unitlocalised.current

interface UnitSpecifiedWeatherEntry  {
    val cloudCover: Int
    val feelsLike: Int
    val humidity: Int
    val observationTime: String
    val pressure: Int
    val temperature: Int
    val precipitation: Int
    val uvIndex: Int
    val visibility: Int
    val weatherCode: Int
    val weatherDescriptions: List<String>
    val weatherIcons: List<String>
    val windDegree: Int
    val windDirection: String
    val windSpeed: Int
}