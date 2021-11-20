package com.example.weatherhut.data.response.weatherapi.forecast


import com.google.gson.annotations.SerializedName

data class Forecastday(

    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val day: Day,
    val hour: List<Hour>
)