package com.example.weatherhut.data.apiservice


import com.example.weatherhut.data.network.ConnectivityIntercepter
import com.example.weatherhut.data.response.openweather.forecast.daily.ForecastWeatherResponseNEW
import com.example.weatherhut.data.response.openweather.forecast.hourly.HourlyForecastResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY1 = "63e7a21b082b4161bbc4d77b8c021546"

//https://api.weatherbit.io/v2.0/current?lat=35.7796&lon=-78.6382&key=63e7a21b082b4161bbc4d77b8c021546&include=hourly

interface WeatherBitApiService {

    @GET("daily")
    fun getForecastDayWeather(
        @Query("city") city: String = "Delhi"
    ): Deferred<ForecastWeatherResponseNEW>

    @GET("daily")
    fun getForecastDayWeather(
        @Query("lat") latitude: Double? = 55.5555,
        @Query("lon") longitude: Double? = 55.5555
    ): Deferred<ForecastWeatherResponseNEW>

    @GET("hourly")
    fun getForecastHourWeather(
        @Query("city") location: String
    ): Deferred<HourlyForecastResponse>

    @GET("hourly")
    fun getForecastHourWeather(
        @Query("lat") latitude: Double? = 55.5555,
        @Query("lon") longitude: Double? =55.5555
    ): Deferred<HourlyForecastResponse>



    companion object {

        operator fun invoke(
            connectivityIntercepter: ConnectivityIntercepter
        ): WeatherBitApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY1)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(connectivityIntercepter)
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherbit.io/v2.0/forecast/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherBitApiService::class.java)
        }
    }
}