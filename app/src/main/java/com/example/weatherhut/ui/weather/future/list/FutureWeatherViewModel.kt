package com.example.weatherhut.ui.weather.future.list

import android.os.Build
import androidx.lifecycle.ViewModel
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.internal.LazyDeferred
import org.threeten.bp.LocalDate


class FutureWeatherViewModel(
    private val weatherHutRepository: WeatherHutRepository
) : ViewModel() {

    val weatherLocation by LazyDeferred {
        weatherHutRepository.getWeatherLocation()
    }

    val futureEntries by LazyDeferred {
        weatherHutRepository.getFetchedFutureWeather(LocalDate.now())
    }

    val currentWeather by LazyDeferred {
        weatherHutRepository.getFetchedCurrentWeather(true)
    }

}