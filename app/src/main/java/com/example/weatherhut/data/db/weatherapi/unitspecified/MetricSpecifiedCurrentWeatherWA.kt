package com.example.weatherhut.data.db.weatherapi.unitspecified

import androidx.annotation.ColorRes
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.weatherhut.data.db.weatherapi.Condition
import com.google.gson.annotations.SerializedName


class MetricSpecifiedCurrentWeatherWA(
    @ColumnInfo(name = "cloud")
    override val cloud: Int,
    @Embedded
    override val condition: Condition,
    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "gustKph")
    override val gust: Double,
    @ColumnInfo(name = "humidity")
    override val humidity: Int,
    @ColumnInfo(name = "isDay")
    override val isDay: Int,
    @ColumnInfo(name = "lastUpdated")
    override val lastUpdated: String,
    @ColumnInfo(name = "lastUpdatedEpoch")
    override val lastUpdatedEpoch: Int,
    @ColumnInfo(name = "precipMm")
    override val precipitation: Double,
    @ColumnInfo(name = "pressureMb")
    override val pressure: Double,
    @ColumnInfo(name = "tempC")
    override val temperature: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double,
    @ColumnInfo(name = "visKm")
    override val visibility: Double,
    @ColumnInfo(name = "windDegree")
    override val windDegree: Int,
    @ColumnInfo(name = "windDir")
    override val windDir: String,
    @ColumnInfo(name = "windKph")
    override val wind: Double,

    ) : UnitSpecifiedCurrentWeatherWA