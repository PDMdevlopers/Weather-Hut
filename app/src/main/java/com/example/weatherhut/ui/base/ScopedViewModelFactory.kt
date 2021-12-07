package com.example.weatherhut.ui.base

import android.app.Application
import androidx.lifecycle.*
import com.example.weatherhut.data.provider.UnitProvider
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.internal.UnitSystem
import com.example.weatherhut.ui.weather.current.CurrentWeatherViewModel

abstract class ScopedViewModelFactory(
    private val weatherHutRepository: WeatherHutRepository,
    private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory() {

    private val unitSystem = unitProvider.getUnitSystem()

    private val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

}