package com.example.weatherhut.data.response.openweather.forecast.daily


import com.example.weatherhut.data.db.weatherbit.ForecastDay
import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponseNEW(
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("data")
    val ForecastWeatherEntries: List<ForecastDay>,
    val lat: String,
    val lon: String,
    @SerializedName("state_code")
    val stateCode: String,
    val timezone: String
)