package com.example.weatherhut.data.response.weatherapi.forecast


import com.google.gson.annotations.SerializedName

data class ForecastWeatherEntries(
    @SerializedName("forecastday")
    val entries: List<Forecastday>
)