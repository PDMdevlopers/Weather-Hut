package com.example.weatherhut.ui.setting

import android.os.Bundle
import android.util.Xml
import androidx.preference.Preference
import com.example.weatherhut.R
import androidx.preference.PreferenceFragmentCompat

class SettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}