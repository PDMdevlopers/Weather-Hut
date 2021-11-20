package com.example.weatherhut.data.response.weatherapi.forecast

import com.google.gson.annotations.SerializedName


data class ForecastWeatherResponse(
    @SerializedName("forecast")
    val forecastWeatherEntries: ForecastWeatherEntries,
    val location: Location
)