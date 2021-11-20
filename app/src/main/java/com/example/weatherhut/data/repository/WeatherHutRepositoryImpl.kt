package com.example.weatherhut.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.weatherhut.data.network.WeatherNetworkDataSource
import com.example.weatherhut.data.provider.LocationProvider
import com.example.weatherhut.data.db.weatherapi.CurrentWeatherWADao
import com.example.weatherhut.data.db.weatherapi.WeatherLocationWADao
import com.example.weatherhut.data.db.weatherapi.unitspecified.UnitSpecifiedCurrentWeatherWA
import com.example.weatherhut.data.db.weatherbit.ForecastDay
import com.example.weatherhut.data.db.weatherbit.ForecastDayDao
import com.example.weatherhut.data.db.weatherbit.ForecastHourEntries
import com.example.weatherhut.data.db.weatherbit.FutureHourDao
import com.example.weatherhut.data.response.weatherapi.CurrentWeatherResponseFromWA
import com.example.weatherhut.data.unitlocalised.future.SimpleUnitSpecifiedFutureEntry
import kotlinx.coroutines.*
import org.threeten.bp.*
import java.sql.Timestamp
import java.sql.Timestamp.UTC
import java.util.*

const val FUTURE_DAYS_COUNT = 16


class WeatherHutRepositoryImpl(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val currentWeatherWADao: CurrentWeatherWADao,
    private val forecastDayDao: ForecastDayDao,
    private val weatherLocationWADao: WeatherLocationWADao,
    private val hourDao: FutureHourDao,
    private val locationProvider: LocationProvider
) : WeatherHutRepository {


    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentData.observeForever { newCurrentWeatherResponse->
                persistFetchedCurrentWeather(newCurrentWeatherResponse)
            }
            downloadedForecastDayData.observeForever { newFutureWeatherResponse->
                persistFetchedFutureWeather(newFutureWeatherResponse.ForecastWeatherEntries)
            }
            downloadedForecastHourData.observeForever { newHourlyWeatherRsponse->
                persistFetchedHourWeatherEntry(newHourlyWeatherRsponse.forecastHourEntries)
            }
        }
    }

    override suspend fun getFetchedCurrentWeather(metric: Boolean): LiveData<out UnitSpecifiedCurrentWeatherWA> {

        return withContext(Dispatchers.IO){
            initWeather()
            //there is no metric system. unitSpecified is used by only one class so return that on both conditions?
            // TODO create a unitSpecified classes if api allow us
            return@withContext if(metric) currentWeatherWADao.getMetricCurrentWeather()
                else currentWeatherWADao.getImperialCurrentWeather()

        }
    }


    override suspend fun getFetchedFutureWeather(startDate: LocalDate): LiveData<List<SimpleUnitSpecifiedFutureEntry>> {
        return withContext(Dispatchers.IO){
            initWeather()
            return@withContext forecastDayDao.getFutureWeather(startDate)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<com.example.weatherhut.data.db.weatherapi.WeatherLocation> {
        return withContext(Dispatchers.IO){
            initWeather()
            return@withContext weatherLocationWADao.getLocation()
        }
    }

    override suspend fun getFetchedHourlyForecastWeather(currentDateTime: LocalDateTime): LiveData<List<ForecastHourEntries>> {
        return withContext(Dispatchers.IO) {
            initWeather()
            return@withContext hourDao.getHourlyWeather(currentDateTime)
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponseFromWA) {
        GlobalScope.launch(Dispatchers.IO){
            currentWeatherWADao.upsert(fetchedWeather.current)
            weatherLocationWADao.upset(fetchedWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(fetchedFutureWeather: List<ForecastDay>) {

        fun deleteOldFetchedData(){
            val today = LocalDate.now()
            forecastDayDao.deleteOldWeatherEntries(today)
        }
        GlobalScope.launch(Dispatchers.IO){
            deleteOldFetchedData()
            forecastDayDao.upsert(fetchedFutureWeather)
            val location = weatherLocationWADao.getLocationNonLive()
            weatherLocationWADao.upset(location)
        }
    }

    private fun persistFetchedHourWeatherEntry(newHourEntries: List<ForecastHourEntries>) {
        fun deleteOldEntries(currentDateTime: LocalDateTime){
            hourDao.deleteOldHourlyWeatherEntries()
        }

        GlobalScope.launch(Dispatchers.IO){
            deleteOldEntries(LocalDateTime.now())
            hourDao.upsert(newHourEntries)
            val location = weatherLocationWADao.getLocationNonLive()
            weatherLocationWADao.upset(location)
        }
    }

    private suspend fun initWeather(){
        val lastWeatherLocation = weatherLocationWADao.getLocationNonLive()

        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather()
            fetchHourlyWeather()
            return
        }

        if (isFetchCurrentWeatherNeeded(lastWeatherLocation.zoneDateTime))
            fetchCurrentWeather()

        if(isFetchFutureWeatherNeeded())
            fetchFutureWeather()

        if((isFetchHourlyWeatherNeeded())){
            fetchHourlyWeather()
            }
    }


    private suspend fun fetchCurrentWeather(){

        weatherNetworkDataSource.fetchCurrentWeatherData(
            locationProvider.getPreferredLocation()
        )
    }

    private suspend fun fetchFutureWeather(){
        weatherNetworkDataSource.getForecastDayWeather(
            locationProvider.getPreferredLocation()
        )
    }

    private suspend fun fetchHourlyWeather(){
        weatherNetworkDataSource
            .getForecastHourWeather(locationProvider.getPreferredLocation())
    }


    private fun isFetchCurrentWeatherNeeded(lastFetchedTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchFutureWeatherNeeded(): Boolean {
        val today = LocalDate.now()
        return forecastDayDao.countFutureWeather(today) < FUTURE_DAYS_COUNT
    }

    private fun isFetchHourlyWeatherNeeded(): Boolean {
        return hourDao.getHourWeatherCount(LocalDateTime.now()) < 48
    }
}