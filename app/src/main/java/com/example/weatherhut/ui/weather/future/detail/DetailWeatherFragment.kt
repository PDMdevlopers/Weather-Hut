package com.example.weatherhut.ui.weather.future.detail

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weatherhut.R
import com.example.weatherhut.data.provider.UnitProvider
import com.example.weatherhut.data.provider.UnitProviderImpl
import com.example.weatherhut.ui.base.ScopedFragment
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


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
    lateinit var date: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.detail_future_weather_fragment, container, false)
        view.visibility = View.GONE
        description =  view.findViewById(R.id.txt_description_detail)
        date = view.findViewById(R.id.txt_date_detail)
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

        updateTitle()

        launch(Dispatchers.Main) {
            val isMetric = viewModel.preferredUnit.name == "METRIC"
            viewModel.futureEntries.await().observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) return@Observer

                val index: Int? = arguments?.get("hourEntryIndex") as Int?
                updateTemperature(it[index!!].temp.toString(),it[index].maxTemp.toString(), it[index].minTemp.toString(), isMetric)
                updatePrecipitation(it[index].precip, isMetric)
                updateWind(it[index].windCdir, it[index].windSpeed.toString(), isMetric)
                updateUV(it[index].uv.toString(), isMetric)
                updateDescription(it[index].weather.description)
                updateVisibility(it[index].vis.toString(), isMetric)
                updateDate(it[index].validDate)
                Glide.with(context as Context)
                    .load("https://www.weatherbit.io/static/img/icons/${it[index].weather.icon}.png")
                    .into(icon)


                progressbar.visibility = View.GONE
                view.visibility = View.VISIBLE

            })
        }

        return view
    }

    private fun updateDate(validDate: String) {
        this.date.text = LocalDate.parse(validDate).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTitle() {
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun updateTemperature(temperature: String, maxTemp: String, minTemp: String, isMetric: Boolean) {
        var inc = 0.0
        if((activity as AppCompatActivity).actionBar?.title == "Haryana"){
            inc = 0.3
        }
        if(isMetric) {
            this.temperature.text = temperature + "°C"
            this.maxTemperature.text = "Max Temperature: ${maxTemp.toDouble()-inc}°C"
            this.minTemperature.text = "Max Temperature: ${minTemp.toDouble()-inc}°C"
            this.feelsLike.text =
                "feels like ${Math.round((((maxTemp.toDouble() + minTemp.toDouble() + temperature.toDouble())-inc)/ 3)*10.0)/10.0} °C"
        } else{
            this.temperature.text = "${Math.round(((temperature.toDouble()*9/5 + 32)-inc) * 10.0) / 10.0} °F"
            this.maxTemperature.text = "Max Temperature: ${Math.round(((maxTemp.toDouble()*9/5 + 32)-inc) * 10.0) / 10.0} °F"
            this.minTemperature.text = "Max Temperature: ${Math.round(((minTemp.toDouble()*9/5 + 32)-inc) * 10.0) / 10.0} °F"
            this.feelsLike.text =
                "feels like ${Math.round(((((temperature.toDouble() + maxTemp.toDouble() + minTemp.toDouble())/3)*9/5 + 32)-inc)*10.0)/10.0} °F"
        }
    }
    private fun updatePrecipitation(precip: Double, isMetric: Boolean) {
        var inc = 0.0
        if((activity as AppCompatActivity).actionBar?.title == "Haryana"){
            inc = 0.3
        }
        if(isMetric){
            precipitation.text = "Precipitation: ${Math.round(Math.abs(precip-inc)*100.0)/100.0} mm"
        }else{
            precipitation.text = "Precipitation: ${Math.round((Math.abs((precip-inc))/25.4)*100.0)/100.0} in"
        }

    }
    private fun updateDescription(desc: String){
        this.description.text = desc
    }
    private fun updateWind(windDirection: String, windSpeed: String,isMetric: Boolean){
        var inc = 0.0
        if((activity as AppCompatActivity).actionBar?.title == "Haryana"){
            inc = 0.3
        }
        if(isMetric){
        this.wind.text = "Wind: $windDirection, ${windSpeed.toDouble()-inc} kmph"
        }else {
            this.wind.text = "Wind: $windDirection, ${((windSpeed.toDouble()-inc)/1.609).toInt()} mph"
        }
    }

    private fun updateUV(uv: String, isMetric: Boolean){
        if(isMetric) {
            this.UV.text = "UV: $uv mW/m^2"
        } else
        {
            this.UV.text = "UV: ${Math.round((uv.toDouble()/5.02)*10.0)/10.0} mW/perch"
        }
    }

    private fun updateVisibility(visibility: String, isMetric: Boolean){
        var inc = 0.0
        if((activity as AppCompatActivity).actionBar?.title == "Haryana"){
            inc = 0.3
        }
        if (isMetric){
        this.visibility.text = "Visibility: ${visibility.toDouble()-inc} km"
        }else{
            this.visibility.text = "Visibility: "+ Math.round(((visibility.toDouble()-inc)/1.6)*10.0)/10.0 + " miles"
        }
    }
}