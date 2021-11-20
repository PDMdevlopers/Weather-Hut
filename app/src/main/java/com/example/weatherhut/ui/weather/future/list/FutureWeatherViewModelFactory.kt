package com.example.weatherhut.ui.weather.future.list

import android.annotation.SuppressLint
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.internal.PreferenceProvider

lateinit var loadingGroup: Group

class FutureWeatherViewModelFactory(
    private val weatherHutRepository: WeatherHutRepository
): ViewModelProvider.NewInstanceFactory() {


    @SuppressLint("NewApi")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FutureWeatherViewModel(weatherHutRepository) as T
    }
}