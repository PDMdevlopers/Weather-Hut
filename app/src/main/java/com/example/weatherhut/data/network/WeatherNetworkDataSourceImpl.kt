package com.example.weatherhut.data.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherhut.data.apiservice.ApixuWeatherApiService
import com.example.weatherhut.data.apiservice.WeatherBitApiService
import com.example.weatherhut.data.provider.UNIT_SYSTEM
import com.example.weatherhut.data.response.openweather.forecast.daily.ForecastWeatherResponseNEW
import com.example.weatherhut.data.response.openweather.forecast.hourly.HourlyForecastResponse
import com.example.weatherhut.data.response.weatherapi.CurrentWeatherResponseFromWA
import com.example.weatherhut.internal.NoConnectivityException


class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService,
    private val weatherBitApiService: WeatherBitApiService) : WeatherNetworkDataSource {

    private val _downloadedCurrentData = MutableLiveData<CurrentWeatherResponseFromWA>()
    override val downloadedCurrentData: LiveData<CurrentWeatherResponseFromWA>
        get() = _downloadedCurrentData

    override suspend fun fetchCurrentWeatherData(location: String) {

        try {
            if(location[0] == '/') {
                var latLon: Array<Double?> = getLatLon(location)
                println(latLon[1])
                val fetchWeatherData = apixuWeatherApiService
                    .getCurrentWeather(latLon[0], latLon[1])
                    .await()
                _downloadedCurrentData.postValue(fetchWeatherData)
            } else{
                val fetchWeatherData = apixuWeatherApiService
                    .getCurrentWeather(location)
                    .await()
                _downloadedCurrentData.postValue(fetchWeatherData)
            }
        } catch (e: NoConnectivityException){
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }

    private fun getLatLon(location: String): Array<Double?> {
        var latLon: Array<Double?> = arrayOfNulls(2)
        var i = 1
        while(location[i] != ','){
            i++
        }
        latLon[0] = location.substring(1, i).toDouble()
        var j = i+1
        while (location[j] != '/'){
            j++
        }
        latLon[1] = location.substring(i+1, j).toDouble()
        return latLon
    }

    val _downloadedForecastData = MutableLiveData<ForecastWeatherResponseNEW>()
    override val downloadedForecastDayData: LiveData<ForecastWeatherResponseNEW>
        get() = _downloadedForecastData

    override suspend fun getForecastDayWeather(location: String) {
        try {
            weatherBitApiService.apply {
                if(location[0] == '/') {
                    val latLon = getLatLon(location)
                    val forecastWeather = getForecastDayWeather(latLon[0], latLon[1]).await()
                    _downloadedForecastData.postValue(forecastWeather)
                } else{
                    val forecastWeather = getForecastDayWeather(location).await()
                    _downloadedForecastData.postValue(forecastWeather)
                }
            }
        } catch (e: NoConnectivityException){
            Log.e("No Internet", "Check you internet connection")
        }
    }

    override suspend fun getForecastDayWeather(latitude: Double, longitude: Double) {
        try {

            val forecastWeather = weatherBitApiService.getForecastDayWeather(latitude, longitude).await()
            _downloadedForecastData.postValue(forecastWeather)
            System.out.println("{{{{{{{{{{{{{}{{{{{{{{{{{}}}}}}}}}}}}chla he ye")
        } catch (e: NoConnectivityException){
            Log.e("No Internet", "Check you internet connection", e)
        }
    }

    val _downloadedForecastHourData = MutableLiveData<HourlyForecastResponse>()
    override val downloadedForecastHourData: LiveData<HourlyForecastResponse>
    get() = _downloadedForecastHourData

    override suspend fun getForecastHourWeather(location: String) {
        try {
            val forecastHourWeather = weatherBitApiService.getForecastHourWeather(location).await()
            _downloadedForecastHourData.postValue(forecastHourWeather)
            System.out.println("{{{{{{{{{{{{{}{{{{{{{{{{{}}}}}}}}}}}}chla he ye")
        } catch(e: NoConnectivityException){
            Log.e("No Internet", "Check your internet connection", e)
        }
    }

}