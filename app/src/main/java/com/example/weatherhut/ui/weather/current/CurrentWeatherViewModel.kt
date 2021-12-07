package com.example.weatherhut.ui.weather.current

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.weatherhut.data.provider.LocationSettingProvider

import com.example.weatherhut.data.provider.UnitProvider
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.internal.LazyDeferred
import com.example.weatherhut.internal.PreferenceProvider
import com.example.weatherhut.internal.UnitSystem
import org.threeten.bp.LocalDateTime


class CurrentWeatherViewModel(
    private val weatherHutRepository: WeatherHutRepository,
    unitProvider: UnitProvider,
    locationSettingProvider: LocationSettingProvider
) : ViewModel() {

    val unitSystem = unitProvider.getUnitSystem()
    val isLocationOn = locationSettingProvider.getLocationSetting()

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