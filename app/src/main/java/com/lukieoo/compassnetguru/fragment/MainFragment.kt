package com.lukieoo.compassnetguru.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lukieoo.compassnetguru.R
import com.lukieoo.compassnetguru.ui.MainViewModel
import com.lukieoo.compassnetguru.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


private const val PERMISSION_REQUEST = 10

@AndroidEntryPoint
class MainFragment constructor() : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var compass: Compass

    @Inject
    lateinit var localization: Localization

    lateinit var navController: NavController

    private lateinit var myCoordinates: MyCoordinates

    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCompass()

        initViewModel()

        initLocalization()


        initView()


    }

    private fun drawNumeral(canvas: Canvas, widthPosition: Float, heightPosition: Float) {


        var paint = Paint()
        paint.textSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 43f, resources.displayMetrics)
        var rect: Rect = Rect()
        var numbers: MutableList<Int> = arrayListOf()
        for (x in 1..60) {
            numbers.add(x)
        }
        for (angle in 1..360) {

            var x: Int =
                (widthPosition / 2 + Math.cos(Math.toRadians(angle.toDouble())) * (widthPosition - 250) / 2 - rect.width() / 2).toInt()
            var y: Int =
                (widthPosition / 2 + Math.sin(Math.toRadians(angle.toDouble())) * (widthPosition - 250) / 2 - rect.width() / 2).toInt()
            canvas.drawText("-", x.toFloat(), y.toFloat(), paint)

        }
    }

    private fun initView() {


        btnCoordinates.setOnClickListener {
            navController = Navigation.findNavController(it)
            navController!!.navigate(R.id.action_mainFragment_to_insertFragment)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        myCoordinates = MyCoordinates(requireContext())

        activity?.let {
            viewModel.getCoordinates().observe(it, Observer {

                if (degreeTitle != null) {
                    var calculateDistance: CalculateDistance = CalculateDistance()

//                    degreeTitle!!.text =
//                        "Distance from the destination: " + (calculateDistance.distance(
//                            it.latitude,
//                            it.longitude,
//                            myCoordinates.getLatitude(),
//                            myCoordinates.getLongitude()
//                        ) * 1000).toInt() + "m"
                    var angle = calculateDistance.az1(

                        myCoordinates.getLatitude(),
                        myCoordinates.getLongitude(),
                            it.latitude,
                            it.longitude
                    )

                    degreeTitle!!.text =
                        "Distance from the destination: " + angle


                    var myBitmap: Bitmap = BitmapFactory.decodeResource(
                        resources,
                        R.drawable.img_compass
                    )
                    var paint = Paint()
                    paint.color = Color.RED
                    paint.style = Paint.Style.FILL
                    paint.strokeWidth = 100f

                    val tempBitmap =
                        Bitmap.createBitmap(
                            myBitmap.width,
                            myBitmap.height,
                            Bitmap.Config.ARGB_8888
                        )
                    val tempCanvas = Canvas(tempBitmap)
                    tempCanvas.drawBitmap(myBitmap, 0f, 0f, paint)
                    tempCanvas.drawCircle(myBitmap.width / 2f, myBitmap.height / 2f, 50f, paint)

                    println("angle" + angle.toDouble())

                    var x: Int =
                        (myBitmap.width / 2f + Math.cos(Math.toRadians(angle.toDouble()-90)) * (myBitmap.width-90 ) / 2 ).toInt()
                    var y: Int =
                        (myBitmap.height / 2f + Math.sin(Math.toRadians(angle.toDouble()-90)) * (myBitmap.height-90 ) / 2).toInt()

                    tempCanvas.drawCircle(x.toFloat(), y.toFloat(), 150f, paint)
//                    tempCanvas.drawText("314", x.toFloat(), y.toFloat(), paint)
                    imageViewCompass.setImageDrawable(BitmapDrawable(resources, tempBitmap))
                }
            })
        }
    }

    private fun initCompass() {
        compass.setImageViewCompass(imageViewCompass = imageViewCompass)
        //compass.setDegreeTitle(degreeTitle = degreeTitle)
    }

    private fun initLocalization() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                localization!!.setListenerLocationUpdates(viewModel)
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (context?.let {
                    checkCallingOrSelfPermission(
                        it,
                        permissionArray[i]
                    )
                } == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            activity,
                            "Go to settings and enable the permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            if (allSuccess) {
                localization!!.setListenerLocationUpdates(viewModel)
            }


        }
    }

    override fun onResume() {
        super.onResume()
        compass.onResume()
    }

    override fun onPause() {
        super.onPause()
        compass.onPause()
    }
}