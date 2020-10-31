package com.lukieoo.compassnetguru.utils

import android.content.Context

/**
 *  Class is a handle of Shared preferences data for destination
 */
class MyCoordinates (context: Context){
    
    private val PREFERENCES_NAME="SHARED_PREFRENCES"
    private val PREFERENCES_LATITUDE="latitude"
    private val PREFERENCES_LONGITUDE="longitude"
    
    private val preference=context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE)

    fun getLatitude():Double{
        return preference.getString(PREFERENCES_LATITUDE,"0.0")!!.toDouble()
    }

    fun setLatitude(latitude:Double){
        val editor=preference.edit()
        editor.putString(PREFERENCES_LATITUDE,latitude.toString())
        editor.apply()
    }

    fun getLongitude():Double{
        return preference.getString(PREFERENCES_LONGITUDE,"0.0")!!.toDouble()
    }

    fun setLongitude(longitude:Double){
        val editor=preference.edit()
        editor.putString(PREFERENCES_LONGITUDE,longitude.toString())
        editor.apply()
    }
}