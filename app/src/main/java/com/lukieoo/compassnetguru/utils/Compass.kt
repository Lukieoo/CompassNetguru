package com.lukieoo.compassnetguru.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

/**
 * The class responsible for the compass
 * Mandatory method :
 * @param setImageViewCompass set compass image
 */

class Compass(private val context: Context) : SensorEventListener {

    private var currentDegree = 0f

    private var mSensorManager: SensorManager? = null

    private var imageViewCompass: ImageView? = null


    init {
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }


    /**
     * Mandatory ! Init your compass imageView here
     */
    fun setImageViewCompass(imageViewCompass: ImageView?) {
        this.imageViewCompass = imageViewCompass
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree = Math.round(event!!.values[0]).toFloat()

        // create a rotation animation (reverse turn degree degrees)
        val ra = RotateAnimation(
            currentDegree,
            -degree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        // how long the animation will take place
        ra.duration = 210

        // set the animation after the end of the reservation status
        ra.fillAfter = true

        // Start the animation
        imageViewCompass!!.startAnimation(ra)
        currentDegree = -degree
    }

    fun onResume() {
        mSensorManager!!.registerListener(
            this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    fun onPause() {
        // to stop the listener and save battery
        mSensorManager!!.unregisterListener(this)
    }
}