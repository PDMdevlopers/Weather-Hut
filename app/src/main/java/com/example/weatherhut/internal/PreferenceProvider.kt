package com.example.weatherhut.internal

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    val preference: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}