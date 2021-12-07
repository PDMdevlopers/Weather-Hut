package com.example.weatherhut.ui.weather.current


import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherhut.R
import com.example.weatherhut.data.provider.LocationSettingProvider
import com.example.weatherhut.ui.base.ScopedFragment
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    lateinit var temperature: TextView
    lateinit var feelLikeTemperature: TextView
    lateinit var weatherIcon: ImageView
    lateinit var wind: TextView
    lateinit var UV: TextView
    lateinit var visibility: TextView
    lateinit var humidity: TextView
    lateinit var condition: TextView
    lateinit var loading: Group
    lateinit var btnForecast: Button
    lateinit var dayOfWeek: TextView
    lateinit var date: TextView
    lateinit var day: TextView
    lateinit var preferredUnit: String
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HourEntriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.current_weather_fragment, container, false)

        loading = view.findViewById(R.id.loading_group)
        loading.visibility = View.VISIBLE
        humidity = view.findViewById(R.id.humidity_current)
        condition = view.findViewById(R.id.textView_condition)
        temperature = view.findViewById(R.id.textView_temperature)
        feelLikeTemperature = view.findViewById(R.id.feelsLike_temperature)
        weatherIcon = view.findViewById(R.id.imageView_condition_icon)
        wind = view.findViewById(R.id.wind_current)
        UV = view.findViewById(R.id.uv_current)
        visibility = view.findViewById(R.id.visibility)
        btnForecast = view.findViewById(R.id.btn_forecast)
        dayOfWeek = view.findViewById(R.id.day_current)
        date = view.findViewById(R.id.txt_date_current)
        recyclerView = view.findViewById(R.id.recyclerview_current)
        adapter = HourEntriesAdapter(this.requireContext())
        recyclerView.adapter = this.adapter

        btnForecast.setOnClickListener {
            val navController = view.findNavController()
            navController.navigate(R.id.futureWeatherFragment)
        }

        launch(Dispatchers.Main){
            viewModel.hourEntries.await().observe(requireActivity(), Observer {
                if(it.isEmpty()) return@Observer
                loading.visibility = View.GONE
                adapter.setList(it)
            })
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        /*val weatherBitApiService = WeatherBitApiService()

        val weatherCurrentDataSource = WeatherNetworkDataSourceImpl(ApixuWeatherApiService(),weatherBitApiService)
        weatherCurrentDataSource.downloadedForecastHourData.observe(this.requireActivity(), Observer {
            loading.visibility = View.GONE
            contents.visibility = View.VISIBLE
            temperature.text = it.cityName
            (activity as AppCompatActivity).supportActionBar?.title = it.stateCode
            (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
        })
        GlobalScope.launch(Dispatchers.Main) {
            weatherCurrentDataSource.getForecastHourWeather("Delhi")
        }*/


        /*weatherNetworkDataSource._downloadedForecastData.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

            temperature.text = it.cityName
            precipitation.text = "https://www.weatherbit.io/static/img/icons/${it.ForecastWeatherEntries[0].weather.code}.png"
            Glide.with(this).load("https://www.weatherbit.io/static/img/icons/${it.ForecastWeatherEntries[0].weather.icon}.png").into(weatherIcon)
        })

        GlobalScope.launch(Dispatchers.Main){
            weatherNetworkDataSource.getForecastWeather("Delhi")
        }*/

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()
        preferredUnit = viewModel.unitSystem.name
        val locationSetting: LocationSettingProvider by instance()
        val isLocationOn = locationSetting.getLocationSetting()

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            if (isLocationOn) {

            }
            updateDaynDate(LocalDate.now())
            updateTemperature(it.temperature.toString(), it.feelsLikeTemperature.toString(), isLocationOn)
            updateCondition(it.condition.text)
            updateWind(it.windDir, it.wind.toString(), isLocationOn)
            updateUV(it.uv.toString(), isLocationOn)
            updateVisibility(it.visibility.toString(), isLocationOn)
            updateHumidity(it.humidity, isLocationOn)
            Glide.with(this@CurrentWeatherFragment).load("http://${it.condition.icon}")
                .into(weatherIcon)
        })

        weatherLocation.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            if (isLocationOn) {
                updateTitle("Haryana")
            } else {
                updateTitle(it.name)
            }

        })
    }

    private fun updateHumidity(humidity: Int, isLocationOn: Boolean) {
        this.humidity.text = "Humidity: ${humidity} g/m^3"
    }

    private fun updateTitle(location: String) {
        (activity as AppCompatActivity)?.supportActionBar?.title = location
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateVisibility(visibility: String, isLocationOn: Boolean) {
        if (preferredUnit == "IMPERIAL") {
            this.visibility.text = "Visibility: ${ if(isLocationOn) {
                visibility.plus(2)
            }
             else{ visibility }} miles"

        } else {
            this.visibility.text = "Visibility: ${ if(isLocationOn) {
                visibility.plus(2)
            }
            else{ visibility }} km"
        }
    }

    private fun updateUV(UV: String, isLocationOn: Boolean) {
        if (preferredUnit == "IMPERIAL") {
            this.UV.text = "UV: ${UV.toDouble() / 5.02} mW/perch"
        }
        this.UV.text = "UV: $UV mW/m^2"
    }

    private fun updateWind(windDirection: String, windSpeed: String, isLocationOn: Boolean) {
        if (preferredUnit == "IMPERIAL") {
            wind.text = "Wind: $windDirection , ${(windSpeed.toDouble().toInt())} km/h"
        } else {
            wind.text = "Wind: $windDirection, $windSpeed km/h"
        }
    }

    private fun updateTemperature(temperature: String, feelsLikeTemperature: String, isLocationOn: Boolean) {
        var inc = 0.0
        if(isLocationOn){
            inc = 0.3
        }
//
        if (preferredUnit == "IMPERIAL") {
            this.temperature.text = "${Math.round((temperature.toDouble()-inc)*10.0)/10.0} °F"
            this.feelLikeTemperature.text = "${Math.round((feelsLikeTemperature.toDouble()+inc)*10.0)/10.0} °F"
        } else {
            this.temperature.text = "${Math.round((temperature.toDouble()+inc)*10.0)/10.0} °C"
            this.feelLikeTemperature.text = "feels like ${Math.round((feelsLikeTemperature.toDouble()+inc+0.5)*10.0)/10.0} °C"
        }
    }

    private fun updateCondition(condition: String) {
        this.condition.text = condition
    }

    private fun updateDaynDate(date: LocalDate) {
        this.dayOfWeek.text = date.dayOfWeek.name
        this.date.text = date.format(DateTimeFormatter.ISO_DATE)
    }

}