package com.example.weatherhut.data.provider

import android.content.Context
import com.example.weatherhut.internal.PreferenceProvider

const val DEVICE_LOCATION = "USE_DEVICE_LOCATION"

class LocationSettingProviderImpl(context: Context) : PreferenceProvider(context),LocationSettingProvider {
    override fun getLocationSetting(): Boolean {
        return preference.getBoolean(DEVICE_LOCATION, false)
    }

    override fun setLocationOn() {
        preference.edit().putBoolean("USE_DEVICE_LOCATION", true).apply()
    }
}