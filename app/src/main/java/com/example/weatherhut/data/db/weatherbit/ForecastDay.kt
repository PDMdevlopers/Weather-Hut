package com.example.weatherhut.data.db.weatherbit


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.StringBufferInputStream

@Entity(tableName = "future_weather", indices = [Index(value = ["datetime"], unique = true)])
data class ForecastDay(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    @SerializedName("app_max_temp")
    val appMaxTemp: Double,
    @SerializedName("app_min_temp")
    val appMinTemp: Double,
    val clouds: Int,
    @SerializedName("clouds_hi")
    val cloudsHi: Int,
    @SerializedName("clouds_low")
    val cloudsLow: Int,
    @SerializedName("clouds_mid")
    val cloudsMid: Int,
    val datetime: String,
    val dewpt: Double,
    @SerializedName("high_temp")
    val highTemp: Double,
    @SerializedName("low_temp")
    val lowTemp: Double,
    //@SerializedName("max_dhi")
    //val maxDhi: Any,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("moon_phase_lunation")
    val moonPhaseLunation: Double,
    @SerializedName("moonrise_ts")
    val moonriseTs: Int,
    @SerializedName("moonset_ts")
    val moonsetTs: Int,
    val ozone: Double,
    val pop: Int,
    val precip: Double,
    val pres: Double,
    val rh: Int,
    val slp: Double,
    val snow: Int,
    @SerializedName("snow_depth")
    val snowDepth: Int,
    @SerializedName("sunrise_ts")
    val sunriseTs: Long,
    @SerializedName("sunset_ts")
    val sunsetTs: Long,
    val temp: Double,
    val ts: Long,
    val uv: Double,
    @SerializedName("valid_date")
    val validDate: String,
    val vis: Double,
    @Embedded
    val weather: Weather,
    @SerializedName("wind_cdir")
    val windCdir: String,
    @SerializedName("wind_cdir_full")
    val windCdirFull: String,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_gust_spd")
    val windGustSpd: Double,
    @SerializedName("wind_spd")
    val windSpd: Double
)