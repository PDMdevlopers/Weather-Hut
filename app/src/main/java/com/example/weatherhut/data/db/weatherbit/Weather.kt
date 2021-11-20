package com.example.weatherhut.data.db.weatherbit


import com.google.gson.annotations.SerializedName


data class Weather(
    val code: Int,
    val description: String,
    val icon: String
)