package com.example.weatherhut.data.db.weatherapi.unitspecified

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.weatherhut.data.db.weatherapi.Condition


class ImperialSpecifiedCurrentWeatherWA(
    @ColumnInfo(name = "cloud")
    override val cloud: Int,
    @Embedded
    override val condition: Condition,
    @ColumnInfo(name = "feelslikeF")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "gustMph")
    override val gust: Double,
    @ColumnInfo(name = "humidity")
    override val humidity: Int,
    @ColumnInfo(name = "isDay")
    override val isDay: Int,
    @ColumnInfo(name = "lastUpdated")
    override val lastUpdated: String,
    @ColumnInfo(name = "lastUpdatedEpoch")
    override val lastUpdatedEpoch: Int,
    @ColumnInfo(name = "precipIn")
    override val precipitation: Double,
    @ColumnInfo(name = "pressureIn")
    override val pressure: Double,
    @ColumnInfo(name = "tempF")
    override val temperature: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double,
    @ColumnInfo(name = "visMiles")
    override val visibility: Double,
    @ColumnInfo(name = "windDegree")
    override val windDegree: Int,
    @ColumnInfo(name = "windDir")
    override val windDir: String,
    @ColumnInfo(name = "windMph")
    override val wind: Double,

    ) : UnitSpecifiedCurrentWeatherWA