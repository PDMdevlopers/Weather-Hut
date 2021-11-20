package com.example.weatherhut.data.db.weatherbit




import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime


@Entity(tableName = "hourly_weather", indices = [Index(value = ["datetime"], unique = true)])
data class ForecastHourEntries constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("app_temp")
    val appTemp: Double,
    val clouds: Int,
    @SerializedName("clouds_hi")
    val cloudsHi: Int,
    @SerializedName("clouds_low")
    val cloudsLow: Int,
    @SerializedName("clouds_mid")
    val cloudsMid: Int,
    val datetime: String,
    val dewpt: Double,
    val dhi: Double,
    val dni: Double,
    val ghi: Double,
    val ozone: Double,
    val pod: String,
    val pop: Int,
    val precip: Double,
    val pres: Double,
    val rh: Double,
    val slp: Double,
    val snow: Int,
    @SerializedName("snow_depth")
    val snowDepth: Int,
    @SerializedName("solar_rad")
    val solarRad: Double,
    val temp: Double,
    @SerializedName("timestamp_local")
    val timestampLocal: String,
    @SerializedName("timestamp_utc")
    val timestampUtc: String,
    val ts: Int,
    val uv: Double,
    val vis: Double,
    @Embedded
    val weather: Weather,
    @SerializedName("wind_cdir")
    val windCdir: String,
    @SerializedName("wind_cdir_full")
    val windCdirFull: String,
    @SerializedName("wind_dir")
    val windDir: Int,
    @SerializedName("wind_gust_spd")
    val windGustSpd: Double,
    @SerializedName("wind_spd")
    val windSpd: Double
)
