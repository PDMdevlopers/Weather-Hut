package com.example.weatherhut.data.db.weatherbit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherhut.data.unitlocalised.future.SimpleUnitSpecifiedFutureEntry
import org.threeten.bp.LocalDate

@Dao
interface ForecastDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(forecastDay: List<ForecastDay>)

    @Query("SELECT * FROM future_weather WHERE date(datetime) >= date(:startData)")
    fun getFutureWeather(startData: LocalDate): LiveData<List<SimpleUnitSpecifiedFutureEntry>>

    @Query("SELECT COUNT(id) FROM future_weather WHERE date(datetime) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("DELETE FROM future_weather WHERE date(datetime) < date(:dateToKeep)")
    fun deleteOldWeatherEntries(dateToKeep: LocalDate)
}