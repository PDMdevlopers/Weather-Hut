package com.example.weatherhut.data.provider

import com.example.weatherhut.R

interface IconProvider {
    val icon: HashMap<String, R.id>
    fun getIcon(iconId: String)
}