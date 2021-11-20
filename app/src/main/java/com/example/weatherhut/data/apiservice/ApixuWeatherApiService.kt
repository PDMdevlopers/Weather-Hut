package com.example.weatherhut.data.apiservice


import com.example.weatherhut.data.network.ConnectivityIntercepter
import com.example.weatherhut.data.network.ConnectivityIntercepterImpl
import com.example.weatherhut.data.response.weatherapi.CurrentWeatherResponseFromWA
import com.example.weatherhut.internal.NoConnectivityException
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//const val API_KEY = "783cbe3160a71558226f51d35c91bce6"
const val API_KEY = "6d519be7d06644c6bc3141813211311"

//http://api.weatherapi.com/v1/current.json?key=6d519be7d06644c6bc3141813211311&q=Delhi

//http://api.weatherstack.com/current
//    ? access_key = YOUR_ACCESS_KEY
//    & query = New York

//api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}

interface ApixuWeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
    ): Deferred<CurrentWeatherResponseFromWA>

    @GET
    fun getCurrentWeather(
        @Query("lat") latitude: Double? = 55.5555,
        @Query("lon") longitude: Double? = 55.555
    ): Deferred<CurrentWeatherResponseFromWA>

    companion object {

        operator fun invoke(
            connectivityIntercepter: ConnectivityIntercepter
        ): ApixuWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
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

            //use suspend function instead retrofit with coroutines
            return Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)
        }
    }
}