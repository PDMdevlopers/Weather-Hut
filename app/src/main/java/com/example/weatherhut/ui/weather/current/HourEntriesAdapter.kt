package com.example.weatherhut.ui.weather.current

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherhut.R
import com.example.weatherhut.data.db.weatherbit.ForecastHourEntries
import com.example.weatherhut.data.provider.UnitProvider
import com.example.weatherhut.data.provider.UnitProviderImpl
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class HourEntriesAdapter(private val context: Context): RecyclerView.Adapter<HourEntriesAdapter.HourEntryViewHolder>() {
    var list = ArrayList<ForecastHourEntries>()
    val unitProvider: UnitProvider = UnitProviderImpl(context)
    var index: Int? = null

    class HourEntryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val time = view.findViewById<TextView>(R.id.time_hour_item)
        val icon = view.findViewById<ImageView>(R.id.icon_hour_item)
        val temperature = view.findViewById<TextView>(R.id.temperature_hour_item)
        val day = view.findViewById<TextView>(R.id.txt_day_hour_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourEntryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_hour_weather, parent, false)
        return HourEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourEntryViewHolder, position: Int) {
        val text = list[position].timestampLocal
        holder.time.text = "${LocalDateTime.parse(text).format(DateTimeFormatter.ofPattern("h:mm a"))}"
        if(unitProvider.getUnitSystem().name == "METRIC"){
            holder.temperature.text = "${list[position].temp}°C"
        } else{
            holder.temperature.text = "${Math.round((list[position].temp*9/5 + 32) * 10.0) / 10.0} °F"
        }
        holder.day.text = LocalDateTime.parse(text).dayOfWeek.name
        Glide.with(context).load("https://www.weatherbit.io/static/img/icons/${list[position].weather.icon}.png").into(holder.icon)
    }

    private fun setIndex(position: Int) {
        this.index = position
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<ForecastHourEntries>){
        this.list = ArrayList(list.subList(1, list.size))
        notifyDataSetChanged()
    }
}