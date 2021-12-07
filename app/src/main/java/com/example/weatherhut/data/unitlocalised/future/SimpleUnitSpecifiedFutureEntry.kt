package com.example.weatherhut.data.unitlocalised.future

import androidx.room.ColumnInfo
import androidx.room.Embedded

import com.example.weatherhut.data.db.weatherbit.Weather
import org.threeten.bp.LocalDate


data class SimpleUnitSpecifiedFutureEntry (
    @ColumnInfo(name = "appMaxTemp")
    val appMaxTemp: Double,
    @ColumnInfo(name = "appMinTemp")
    val appMinTemp: Double,
    @ColumnInfo(name = "clouds")
    val clouds: Int,
    @ColumnInfo(name = "cloudsHi")
    val cloudsHi: Int,
    @ColumnInfo(name = "cloudsLow")
    val cloudsLow: Int,
    @ColumnInfo(name = "cloudsMid")
    val cloudsMid: Int,
    @ColumnInfo(name = "datetime")
    val datetime: LocalDate,
    val dewpt: Double,
    @ColumnInfo(name = "highTemp")
    val highTemp: Double,
    @ColumnInfo(name = "lowTemp")
    val lowTemp: Double,
    @ColumnInfo(name = "maxTemp")
    val maxTemp: Double,
    @ColumnInfo(name = "minTemp")
    val minTemp: Double,
    @ColumnInfo(name = "moonPhase")
    val moonPhase: Double,
    @ColumnInfo(name = "moonPhaseLunation")
    val moonPhaseLunation: Double,
    @ColumnInfo(name = "moonriseTs")
    val moonriseTs: Int,
    @ColumnInfo(name = "moonsetTs")
    val moonsetTs: Int,
    @ColumnInfo(name = "ozone")
    val ozone: Double,
    @ColumnInfo(name = "precip")
    val precip: Double,
    @ColumnInfo(name = "pres")
    val pres: Double,
    @ColumnInfo(name = "rh")
    val rh: Int,
    @ColumnInfo(name = "slp")
    val slp: Double,
    @ColumnInfo(name = "snow")
    val snow: Int,
    @ColumnInfo(name = "snowDepth")
    val snowDepth: Int,
    @ColumnInfo(name = "sunriseTs")
    val sunriseTs: Int,
    @ColumnInfo(name = "sunsetTs")
    val sunsetTs: Int,
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "ts")
    val ts: Int,
    @ColumnInfo(name = "uv")
    val uv: Double,
    @ColumnInfo(name = "validDate")
    val validDate: String,
    @ColumnInfo(name = "vis")
    val vis: Double,
    @Embedded
    val weather: Weather,
    @ColumnInfo(name = "windCdir")
    val windCdir: String,
    @ColumnInfo(name = "windCdirFull")
    val windCdirFull: String,
    @ColumnInfo(name = "windDir")
    val windDir: String,
    @ColumnInfo(name = "windGustSpd")
    val windGustSpd: Double,
    @ColumnInfo(name = "windSpd")
    val windSpeed: Double,
    @ColumnInfo(name = "pop")
    val pop: Int,
)