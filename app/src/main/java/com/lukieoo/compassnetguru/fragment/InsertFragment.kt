package com.lukieoo.compassnetguru.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lukieoo.compassnetguru.MainActivity
import com.lukieoo.compassnetguru.R
import com.lukieoo.compassnetguru.ui.MainViewModel
import com.lukieoo.compassnetguru.utils.MyCoordinates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_insert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class InsertFragment constructor() : Fragment(R.layout.fragment_insert) {

    private lateinit var navController: NavController

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var myCoordinates: MyCoordinates

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initView()
    }

    private fun initView() {

        latitude.text = myCoordinates.getLatitude().toString().toEditable()
        longitude.text = myCoordinates.getLongitude().toString().toEditable()

        btnSetCoordinates.setOnClickListener {

            myCoordinates.setLatitude(latitude.text.toString().toDouble())
            myCoordinates.setLongitude(longitude.text.toString().toDouble())

            navController = Navigation.findNavController(it)
            navController.popBackStack()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        activity.let {
            viewModel.getCoordinates().observe(it!!, Observer {
                if (it.latitude != null && it.longitude != null) {
                    if (latitudeCurrent != null && longitudeCurrent != null) {
                        latitudeCurrent.text = it.latitude.toString().toEditable()
                        longitudeCurrent.text = it.longitude.toString().toEditable()
                    }

                } else {
                    if (latitudeCurrent != null && longitudeCurrent != null) {
                        latitudeCurrent.text = "none".toEditable()
                        longitudeCurrent.text = "none".toEditable()
                    }
                }
            })


        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}