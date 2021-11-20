package com.example.weatherhut.ui.weather.current

import androidx.lifecycle.ViewModel

import com.example.weatherhut.data.provider.UnitProvider
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.internal.LazyDeferred
import com.example.weatherhut.internal.UnitSystem
import org.threeten.bp.LocalDateTime


class CurrentWeatherViewModel(
    private val weatherHutRepository: WeatherHutRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    private val isMetric: Boolean
    get() = unitSystem == UnitSystem.METRIC

    val weather by LazyDeferred {
            weatherHutRepository.getFetchedCurrentWeather(isMetric)
    }

    val weatherLocation by LazyDeferred {
        weatherHutRepository.getWeatherLocation()
    }

    val hourEntries by LazyDeferred {

        weatherHutRepository.getFetchedHourlyForecastWeather(LocalDateTime.now())
    }
}