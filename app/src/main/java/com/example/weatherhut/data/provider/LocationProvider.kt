package com.example.weatherhut.data.provider

import com.example.weatherhut.data.db.weatherapi.WeatherLocation


interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocation(): String
}