package com.example.weatherhut.data.provider

import com.example.weatherhut.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}