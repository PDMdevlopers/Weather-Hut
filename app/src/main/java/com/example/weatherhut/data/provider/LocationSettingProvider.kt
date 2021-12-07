package com.example.weatherhut.data.provider

interface LocationSettingProvider {
    fun getLocationSetting(): Boolean;
    fun setLocationOn();
}