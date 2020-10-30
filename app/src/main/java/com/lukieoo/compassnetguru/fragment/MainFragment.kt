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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


private const val PERMISSION_REQUEST = 10

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment constructor() : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var compass: Compass

    @Inject
    lateinit var localization: Localization

    @Inject
    lateinit var targetHolder: TargetHolder

    @Inject
    lateinit var mathematicalOperations: MathematicalOperations

    @Inject
    lateinit var myCoordinates: MyCoordinates

    lateinit var navController: NavController


    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()

        initCompass()

        initViewModel()

        initLocalization()


    }

    private fun initView() {

        btnCoordinates.setOnClickListener {
            navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_mainFragment_to_insertFragment)
        }
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)


        activity?.let {
            viewModel.getCoordinates().observe(it, Observer {

                if (degreeTitle != null) {


                    degreeTitle!!.text =
                        "Distance from the destination: " + (mathematicalOperations.distance(
                            it.latitude,
                            it.longitude,
                            myCoordinates.getLatitude(),
                            myCoordinates.getLongitude()
                        ) * 1000).toInt() + "m"



                    imageViewCompass.setImageDrawable(
                        targetHolder.setTarget(
                            it.latitude,
                            it.longitude,
                            myCoordinates.getLatitude(),
                            myCoordinates.getLongitude()
                        )
                    )
                    targetHolder.clear()
                }
            })
        }
    }

    private fun initCompass() {
        compass.setImageViewCompass(imageViewCompass = imageViewCompass)
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