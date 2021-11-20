package com.example.weatherhut.internal

import android.os.Build

import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object TypeConverter{



    @TypeConverter
    @JvmStatic
    fun stringToDate(str: String) = str?.let {
        if(str.length >= 9) {
            LocalDate.parse(str, DateTimeFormatter.ISO_DATE)
        } else{
            LocalDate.parse(str, DateTimeFormatter.ISO_DATE_TIME)
        }
    }

    @TypeConverter
    fun dateToString(dateTime: LocalDate) = dateTime?.let {
        it.toString()
    }

    @TypeConverter
    fun stringToDateTime(str:String) = str?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy.mm.dd:HH"))
    }

    @TypeConverter
    fun dateTimeToString(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

}
