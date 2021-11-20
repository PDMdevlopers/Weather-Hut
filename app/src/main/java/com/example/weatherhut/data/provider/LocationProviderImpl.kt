package com.example.weatherhut.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.weatherhut.data.db.WeatherHutDatabase_Impl
import com.example.weatherhut.data.repository.WeatherHutRepository
import com.example.weatherhut.data.repository.WeatherHutRepositoryImpl
import com.example.weatherhut.internal.LocationPermissionNotGrantedException
import com.example.weatherhut.internal.PreferenceProvider
import com.example.weatherhut.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred


const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fuseLocationProviderClient: FusedLocationProviderClient,
    val context: Context) : PreferenceProvider(context), LocationProvider {
    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: com.example.weatherhut.data.db.weatherapi.WeatherLocation): Boolean {
        println("hasLocationChanged")
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: com.example.weatherhut.data.db.weatherapi.WeatherLocation): Boolean {
        println("hasCustomLocationChanged")
        if(!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        } else return false
    }

    private fun getCustomLocationName(): String? {
        println("getCustomLocationName")
        return preference.getString(CUSTOM_LOCATION, "Delhi")
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: com.example.weatherhut.data.db.weatherapi.WeatherLocation): Boolean {
        println("hasDeviceLocationChanged")
        if (!isUsingDeviceLocation()) {
            return false
        }
        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonThrashHold = 0.3
        return Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThrashHold &&
                Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThrashHold
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        println("getLastDeviceLocation")
        return if (hasLocationPermission())
            fuseLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        println("hasLocationPermission")
        return ContextCompat.checkSelfPermission(
            appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isUsingDeviceLocation(): Boolean {
        println("isUsingDeviceLocation")
        return preference.getBoolean(USE_DEVICE_LOCATION, true) //TODO: need to check
    }


    override suspend fun getPreferredLocation(): String {
        println("getPreferredLocation")
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation =
                    getLastDeviceLocation().await()
                        ?: return "${getCustomLocationName()}"
                return "/${deviceLocation.latitude},${deviceLocation.longitude}}/"
            } catch (e: LocationPermissionNotGrantedException) {
                return "${getCustomLocationName()}"
            }
        } else return "${getCustomLocationName()}"
    }
}