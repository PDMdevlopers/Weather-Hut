package com.example.weatherhut.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherhut.data.db.weatherapi.WeatherLocation
import com.example.weatherhut.data.db.weatherapi.unitspecified.UnitSpecifiedCurrentWeatherWA
import com.example.weatherhut.data.db.weatherbit.ForecastHourEntries
import com.example.weatherhut.data.unitlocalised.future.SimpleUnitSpecifiedFutureEntry
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

interface WeatherHutRepository {

    suspend fun getFetchedCurrentWeather(metric: Boolean): LiveData<out UnitSpecifiedCurrentWeatherWA>
    suspend fun getFetchedFutureWeather(startDate: LocalDate): LiveData<List<SimpleUnitSpecifiedFutureEntry>>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    suspend fun getFetchedHourlyForecastWeather(currentDateTime: LocalDateTime): LiveData<List<ForecastHourEntries>>
}