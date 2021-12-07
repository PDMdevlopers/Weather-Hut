package com.example.weatherhut


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherhut.data.provider.LocationSettingProvider
import com.example.weatherhut.data.repository.WeatherHutRepositoryImpl
import com.example.weatherhut.internal.LifecycleBoundLocationManager
import com.example.weatherhut.ui.weather.future.list.FutureListItemAdapter
import com.example.weatherhut.ui.weather.future.list.FutureWeatherFragment
import com.example.weatherhut.ui.weather.future.list.FutureWeatherViewModel
import com.example.weatherhut.ui.weather.future.list.FutureWeatherViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.GlobalScope
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1


class MainActivity : AppCompatActivity(), KodeinAware {
    lateinit var navController: NavController
    lateinit var toolbar: Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var appBarConfiguration: AppBarConfiguration
    override val kodein by closestKodein()
    private val locationSettingProvider: LocationSettingProvider by instance()
    private val fusedLocationProvider: FusedLocationProviderClient by instance()

    val viewModel: FutureWeatherViewModel by viewModels()

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //binding
        navController = this.findNavController(R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar)
        drawer = findViewById(R.id.drawer_layout)
        window.statusBarColor = Color.parseColor("#1A237E")

        //setting up toolbar
        setUpToolBar()

        //linking toolbar, navigation drawer and navController together
        appBarConfiguration = AppBarConfiguration(navController.graph, drawer)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navigationView).setupWithNavController(navController)

        //requesting location permission
        requestLocationPermission()

        //if(hasPermissionGranted()){
        // bindLocationManager()
        //} else requestLocationPermission()
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setSubtitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(Color.parseColor("#303F9F"))
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onNavigateUp()
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(this, fusedLocationProvider, locationCallBack)
    }

    private fun hasPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationSettingProvider.setLocationOn()
            //bindLocationManager()
            } else Toast.makeText(this, "Set the location in the setting, manually.", Toast.LENGTH_SHORT).show()
        } else return super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(navController, null)
    }
}