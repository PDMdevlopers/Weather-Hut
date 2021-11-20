package com.example.weatherhut.data.network

import androidx.lifecycle.LiveData
import com.example.weatherhut.data.response.openweather.forecast.daily.ForecastWeatherResponseNEW
import com.example.weatherhut.data.response.openweather.forecast.hourly.HourlyForecastResponse
import com.example.weatherhut.data.response.weatherapi.CurrentWeatherResponseFromWA

interface WeatherNetworkDataSource {
    val downloadedCurrentData: LiveData<CurrentWeatherResponseFromWA>
    val downloadedForecastDayData: LiveData<ForecastWeatherResponseNEW>
    val downloadedForecastHourData: LiveData<HourlyForecastResponse>


    suspend fun fetchCurrentWeatherData(location: String)
    suspend fun getForecastDayWeather(location: String)
    suspend fun getForecastDayWeather(latitude: Double, longitude: Double)
    suspend fun getForecastHourWeather(location: String)
}