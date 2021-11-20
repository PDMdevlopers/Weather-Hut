package com.example.weatherhut.ui.weather.current

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.format.DateFormat
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherhut.R
import com.example.weatherhut.data.db.weatherbit.ForecastHourEntries
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.text.DateFormatSymbols

class HourEntriesAdapter(private val context: Context): RecyclerView.Adapter<HourEntriesAdapter.HourEntryViewHolder>() {
    var list = ArrayList<ForecastHourEntries>()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HourEntryViewHolder, position: Int) {

        val text = list[position].timestampLocal
        holder.time.text = "${LocalDateTime.parse(text).format(
            DateTimeFormatter.ofPattern("h:mm a"))}"
        holder.temperature.text = list[position].temp.toString()
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
        this.list = ArrayList(list)
        notifyDataSetChanged()
    }
}