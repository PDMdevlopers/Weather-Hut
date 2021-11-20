package com.example.weatherhut.data.response.openweather.forecast.hourly


import com.example.weatherhut.data.db.weatherbit.ForecastHourEntries
import com.google.gson.annotations.SerializedName

data class HourlyForecastResponse(
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("data")
    val forecastHourEntries: List<ForecastHourEntries>,
    val lat: String,
    val lon: String,
    @SerializedName("state_code")
    val stateCode: String,
    val timezone: String
)