package com.example.weatherhut.data.provider

import android.content.Context
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.weatherhut.internal.PreferenceProvider
import com.example.weatherhut.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectName = preference.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectName!!)
    }
}