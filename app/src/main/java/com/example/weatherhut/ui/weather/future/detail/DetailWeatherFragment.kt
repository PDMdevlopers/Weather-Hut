package com.example.weatherhut.ui.weather.future.detail

import android.content.Context
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherhut.R
import com.example.weatherhut.ui.base.ScopedFragment
import com.example.weatherhut.ui.weather.future.list.FutureWeatherFragment
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class DetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: DetailedWeatherVIewModelFactory by instance()
    private lateinit var viewModel: DetailWeatherViewModel

    lateinit var temperature: TextView
    lateinit var feelsLike: TextView
    lateinit var icon: ImageView
    lateinit var condition: TextView
    lateinit var description: TextView
    lateinit var visibility: TextView
    lateinit var wind: TextView
    lateinit var maxTemperature: TextView
    lateinit var minTemperature: TextView
    lateinit var precipitation: TextView
    lateinit var UV: TextView
    lateinit var progressbar: LinearProgressIndicator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.detail_future_weather_fragment, container, false)
        view.visibility = View.GONE
        description =  view.findViewById(R.id.txt_description_detail)
        temperature = view.findViewById(R.id.Temperature_detail)
        feelsLike = view.findViewById(R.id.feels_like_detail)
        icon = view.findViewById(R.id.icon_detail)
        condition = view.findViewById(R.id.txt_condition_detail)
        maxTemperature = view.findViewById(R.id.txt_max_temperature)
        minTemperature = view.findViewById(R.id.text_min_temperature)
        precipitation = view.findViewById(R.id.txt_precipitation_detail)
        wind = view.findViewById(R.id.txt_wind_detail)
        UV = view.findViewById(R.id.txt_UV_detail)
        visibility = view.findViewById(R.id.txt_visibility_detail)
        progressbar = view.findViewById(R.id.progress_detail)
        progressbar.visibility = View.VISIBLE
        updateTitl()

        launch(Dispatchers.Main) {
            viewModel.futureEntries.await().observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) return@Observer
                val index: Int? = arguments?.get("hourEntryIndex") as Int?
                updateTemperature(it[index!!].temp.toString(),it[index].maxTemp.toString(), it[index].minTemp.toString())
                updatePrecipitation(it[index].precip)
                updateWind(it[index].windDir.toString(), it[index].windSpeed.toString())
                updateUV(it[index].uv.toString())
                updateDescription(it[index].weather.description)
                updateVisibility(it[index].vis.toString())
                Glide.with(context as Context)
                    .load("https://www.weatherbit.io/static/img/icons/${it[index].weather.icon}.png")
                    .into(icon)
                progressbar.visibility = View.GONE
                view.visibility = View.VISIBLE

            })
        }

        return view
    }

    private fun updateTitl() {
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun updateTemperature(temperature: String, maxTemp: String, minTemp: String) {
        this.temperature.text = temperature
        this.maxTemperature.text = "Max Temperature: ${maxTemp}°C"
        this.minTemperature.text = "Max Temperature: ${minTemp}°C"
    }
    private fun updatePrecipitation(precip: Double) {
        precipitation.text = "Precipitation: " + precip.toString()
    }
    private fun updateDescription(desc: String){
        this.description.text = desc
    }
    private fun updateWind(windDirection: String, windSpeed: String){
        this.wind.text = "Wind: $windSpeed miles"
    }

    private fun updateUV(uv: String){
        this.UV.text = "UV: " + uv
    }

    private fun updateVisibility(visibility: String){
        this.visibility.text = "Visibility: "+ visibility + " miles"
    }
}