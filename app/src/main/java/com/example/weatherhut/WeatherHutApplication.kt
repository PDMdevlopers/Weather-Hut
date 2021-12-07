package com.example.weatherhut

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import com.example.weatherhut.data.apiservice.ApixuWeatherApiService
import com.example.weatherhut.data.apiservice.WeatherBitApiService
import com.example.weatherhut.data.db.WeatherHutDatabase
import com.example.weatherhut.data.network.*
import com.example.weatherhut.data.provider.*
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.data.repository.WeatherHutRepositoryImpl
import com.example.weatherhut.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.weatherhut.ui.weather.future.detail.DetailedWeatherVIewModelFactory
import com.example.weatherhut.ui.weather.future.list.FutureWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class WeatherHutApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherHutApplication))

        bind() from singleton {WeatherHutDatabase(instance())}
        bind() from singleton { instance<WeatherHutDatabase>().CurrentWeatherWADao() }
        bind() from singleton { instance<WeatherHutDatabase>().WeatherLocationWADao() }
        bind() from singleton { instance<WeatherHutDatabase>().ForecastDayDao() }
        bind() from singleton { instance<WeatherHutDatabase>().FutureHourDao() }
        bind<ConnectivityIntercepter>() with singleton { ConnectivityIntercepterImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind() from singleton { WeatherBitApiService(instance())}
        bind() from provider{LocationServices.getFusedLocationProviderClient(instance<Context>())}
        bind<LocationSettingProvider>() with singleton { LocationSettingProviderImpl(instance()) }
        bind<WeatherNetworkDataSourceImpl>() with singleton { WeatherNetworkDataSourceImpl(instance(), instance()) }
        bind<WeatherHutRepository>() with singleton { WeatherHutRepositoryImpl(instance(), instance(), instance(), instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton {UnitProviderImpl(instance())}
        bind<LocationProvider>() with singleton {LocationProviderImpl(instance(), instance())}
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { FutureWeatherViewModelFactory(instance())}
        bind() from provider { DetailedWeatherVIewModelFactory(instance(), instance())}
        }

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
    }
