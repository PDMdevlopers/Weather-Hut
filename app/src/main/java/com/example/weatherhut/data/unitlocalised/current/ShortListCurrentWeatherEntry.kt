package com.example.weatherhut.data.unitlocalised.current

import androidx.room.ColumnInfo

data class ShortListCurrentWeatherEntry(
    @ColumnInfo(name = "cloudCover")
    override val cloudCover: Int,
    @ColumnInfo(name = "feelsLike")
    override val feelsLike: Int,
    @ColumnInfo(name = "humidity")
    override val humidity: Int,
    @ColumnInfo(name = "observationTime")
    override val observationTime: String,
    @ColumnInfo(name = "precipitation")
    override val precipitation: Int,
    @ColumnInfo(name = "pressure")
    override val pressure: Int,
    @ColumnInfo(name = "temperature")
    override val temperature: Int,
    @ColumnInfo(name = "uvIndex")
    override val uvIndex: Int,
    @ColumnInfo(name = "visibility")
    override val visibility: Int,
    @ColumnInfo(name = "weatherDescriptions")
    override val weatherDescriptions: List<String>,
    @ColumnInfo(name = "weatherIcons")
    override val weatherIcons: List<String>,
    @ColumnInfo(name = "windDegree")
    override val windDegree: Int,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Int,
    override val weatherCode: Int

): UnitSpecifiedWeatherEntry