package com.example.weatherhut.data.db

import android.content.Context
import androidx.room.*
import com.example.weatherhut.data.db.weatherapi.Current
import com.example.weatherhut.data.db.weatherapi.CurrentWeatherWADao
import com.example.weatherhut.data.db.weatherapi.WeatherLocation
import com.example.weatherhut.data.db.weatherapi.WeatherLocationWADao
import com.example.weatherhut.data.db.weatherbit.ForecastDay
import com.example.weatherhut.data.db.weatherbit.ForecastDayDao
import com.example.weatherhut.data.db.weatherbit.ForecastHourEntries
import com.example.weatherhut.data.db.weatherbit.FutureHourDao
import com.example.weatherhut.internal.TypeConverter


@Database(
    entities = [Current::class, WeatherLocation::class, ForecastDay::class, ForecastHourEntries::class],
    version = 1
)
@TypeConverters(TypeConverter::class)
abstract class WeatherHutDatabase: RoomDatabase() {

    abstract fun CurrentWeatherWADao(): CurrentWeatherWADao
    //abstract fun CurrentWeatherDao(): CurrentWeatherDao
    //abstract fun CurrentWeatherDao(): CurrentWeatherDao
    abstract fun WeatherLocationWADao(): WeatherLocationWADao
    abstract fun ForecastDayDao(): ForecastDayDao
    abstract fun FutureHourDao(): FutureHourDao

    companion object {
        @Volatile private var instance:WeatherHutDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance?: buildDatabase(context).also { instance = it}
        }
        private fun buildDatabase(context: Context): WeatherHutDatabase {
            return Room.databaseBuilder(context.applicationContext, WeatherHutDatabase::class.java, "WeatherHut.db")
                .build()
        }
    }
}
