package com.example.weatherhut.ui.weather.future.list

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherhut.R
import com.example.weatherhut.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.nio.file.Files.find

class FutureWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    val viewModelfactory: FutureWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_future_weather_fragment, container, false)
        loadingGroup = view.findViewById(R.id.group_loading)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = FutureListItemAdapter(context as Context)
        recyclerView.adapter = adapter

        launch(Dispatchers.Main) {
            viewModel.futureEntries.await().observe(viewLifecycleOwner, Observer {
                if(it == null) return@Observer
                loadingGroup.visibility = View.GONE
                adapter.setList(it)
            }) }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelfactory).get(FutureWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        bindUI()
    }
    private fun bindUI() = launch(Dispatchers.Main) {
        val futureEntries = viewModel.futureEntries.await()
        val currentWeather = viewModel.currentWeather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@FutureWeatherFragment.viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            updateTitle()
        })

        futureEntries.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            loadingGroup.visibility = View.GONE
        })

        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer

        })
    }

    private fun updateTitle() {
        (activity as AppCompatActivity)?.supportActionBar?.title = "Next 16 days"
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = null
    }


}