package com.example.weatherhut.data.response.weatherapi


import com.example.weatherhut.data.db.weatherapi.Current
import com.example.weatherhut.data.db.weatherapi.WeatherLocation

data class CurrentWeatherResponseFromWA(
    val current: Current,
    val location: WeatherLocation
)