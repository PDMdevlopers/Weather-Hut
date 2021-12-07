package com.example.weatherhut.ui.weather.future.list


import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherhut.R
import com.example.weatherhut.data.provider.UnitProvider
import com.example.weatherhut.data.provider.UnitProviderImpl
import com.example.weatherhut.data.unitlocalised.future.SimpleUnitSpecifiedFutureEntry
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class FutureListItemAdapter(
    var context: Context
) : RecyclerView.Adapter<FutureListItemAdapter.FutureListViewHolder>() {
    val unitProvider = UnitProviderImpl(context)
    var futureList = ArrayList<SimpleUnitSpecifiedFutureEntry>()

    class FutureListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val temperature = view.findViewById<TextView>((R.id.textView_temperature_item))
        val date = view.findViewById<TextView>(R.id.textView_date)
        val weatherIcon = view.findViewById<ImageView>(R.id.imageView_condition_icon_item)
        val weatherDescription = view.findViewById<TextView>(R.id.textView_condition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_future_weather, parent, false)
        view.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
        }
        return FutureListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FutureListViewHolder, position: Int) {
        val list = futureList
        var navController: NavController

        holder.apply {

            with(holder.itemView) {


                itemView.setOnClickListener {

                    //val intent = Intent()
                    //intent.putExtra("temperature", list[position].temp)

                    val action =
                        FutureWeatherFragmentDirections.actionFutureWeatherFragmentToDetailWeatherFragment(
                            position+1
                        )

                    navController = Navigation.findNavController(itemView)
                    navController!!.navigate(action)

                }

            }

        }
        if(unitProvider.getUnitSystem().name.equals("METRIC")){
            holder.temperature.text = "${list[position].temp}°C"
        } else{
            holder.temperature.text = "${Math.round((list[position].temp*9/5 + 32) * 10.0) / 10.0} °F"
        }
        holder.weatherDescription.text = list[position].weather.description
        holder.date.text =
            list[position].datetime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
                .toString()
        Glide.with(context)
            .load("https://www.weatherbit.io/static/img/icons/${list[position].weather.icon}.png")
            .into(holder.weatherIcon)
    }

    override fun getItemCount(): Int {
        return futureList.size
    }

    fun setList(list: List<SimpleUnitSpecifiedFutureEntry>) {
        notifyDataSetChanged()
        this.futureList = ArrayList(list.subList(1, list.size))
    }

}