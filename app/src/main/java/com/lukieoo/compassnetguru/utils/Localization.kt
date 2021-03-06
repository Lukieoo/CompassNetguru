package com.lukieoo.compassnetguru.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lukieoo.compassnetguru.model.Coordinates
import com.lukieoo.compassnetguru.ui.MainViewModel

/**
 * Get your current Localization, latitude and longitude
 */
class Localization(private val context: Context) {


    private var locationManager: LocationManager? = null
    private var viewModel: MainViewModel? = null


    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

            viewModel!!.setCoordinates(Coordinates(latitude =location.latitude,longitude = location.longitude ))

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    init {

        locationManager =
            context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?

    }
    /**
     * Mandatory to call , this method active your Location coordinates
     */
    @SuppressLint("MissingPermission")
    open fun setListenerLocationUpdates(viewModel: MainViewModel) {
        this.viewModel = viewModel
        locationManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0L,
            0f,
            locationListener
        )

    }


}