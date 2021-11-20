package com.example.weatherhut.data.db.weatherapi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherLocationWADao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upset(weatherLocation: WeatherLocation)

    @Query("SELECT * FROM weather_location WHERE id = $CURRENT_WEATHER_ID")
    fun getLocation(): LiveData<WeatherLocation>

    @Query("SELECT * FROM weather_location WHERE id = $CURRENT_WEATHER_ID")
    fun getLocationNonLive(): WeatherLocation
}