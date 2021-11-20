package com.example.weatherhut.data.db.weatherbit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.threeten.bp.LocalDateTime

@Dao
interface FutureHourDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(futureHourEntries: List<ForecastHourEntries>)

    @Query("SELECT * FROM hourly_weather WHERE date(timestampLocal) >= date(:currentDateTime)")
    fun getHourlyWeather(currentDateTime: LocalDateTime): LiveData<List<ForecastHourEntries>>

    @Query("SELECT COUNT(id) FROM hourly_weather WHERE date(timestampLocal) <= date(:currentDateTime)")
    fun getHourWeatherCount(currentDateTime: LocalDateTime): Int

    @Query("DELETE FROM hourly_weather")
    fun deleteOldHourlyWeatherEntries()

}
// WHERE date(datetime) < date(:currentDateTime)
// WHERE date(ts) <= date(:currentDateTime)