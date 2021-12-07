package com.example.weatherhut.internal

import android.annotation.SuppressLint
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback

class LifecycleBoundLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
): LifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    val locationRequest = com.google.android.gms.location.LocationRequest().apply {
        interval = 5000
        fastestInterval = 5000
        priority = com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }


    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startLocationUpdate(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}