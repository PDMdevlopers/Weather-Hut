package com.example.weatherhut.data.db.weatherapi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherhut.data.db.weatherapi.unitspecified.ImperialSpecifiedCurrentWeatherWA
import com.example.weatherhut.data.db.weatherapi.unitspecified.MetricSpecifiedCurrentWeatherWA
import com.example.weatherhut.data.db.weatherapi.unitspecified.UnitSpecifiedCurrentWeatherWA
import com.example.weatherhut.data.response.weatherapi.CurrentWeatherResponseFromWA

@Dao
interface CurrentWeatherWADao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(current: Current)

    @Query("SELECT * FROM current_weather_weather_api WHERE id = $CURRENT_WEATHER_ID")
    fun getMetricCurrentWeather(): LiveData<MetricSpecifiedCurrentWeatherWA>

    @Query("SELECT * FROM current_weather_weather_api WHERE id = $CURRENT_WEATHER_ID")
    fun getImperialCurrentWeather(): LiveData<ImperialSpecifiedCurrentWeatherWA>
}