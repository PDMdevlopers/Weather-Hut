package com.example.weatherhut.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.internal.LazyDeferred
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class DetailWeatherViewModel(private val weatherHuRepository: WeatherHutRepository) : ViewModel() {
    val futureEntries by LazyDeferred {
        weatherHuRepository.getFetchedFutureWeather(LocalDate.now())
    }
}