package com.example.weatherhut.data.response.weatherapi.forecast


import com.google.gson.annotations.SerializedName

data class ConditionX(
    val code: Int,
    val icon: String,
    val text: String
)