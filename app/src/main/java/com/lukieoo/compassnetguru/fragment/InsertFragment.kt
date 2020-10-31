package com.lukieoo.compassnetguru.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.lukieoo.compassnetguru.R
import com.lukieoo.compassnetguru.ui.MainViewModel
import com.lukieoo.compassnetguru.Intent.DataState
import com.lukieoo.compassnetguru.Intent.Intent
import com.lukieoo.compassnetguru.utils.MyCoordinates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_insert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.fragment.app.viewModels
import com.lukieoo.compassnetguru.ui.DestinationViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class InsertFragment constructor() : Fragment(R.layout.fragment_insert) {

    private lateinit var navController: NavController

    private lateinit var viewModel: MainViewModel
    private val destinationViewModel: DestinationViewModel by viewModels()

    @Inject
    lateinit var myCoordinates: MyCoordinates

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initDestinationViewModel()
        initView()
    }

    private fun initView() {
        btnSetCoordinates.setOnClickListener {
            try {

                myCoordinates.setLatitude(latitude.text.toString().toDouble())
                myCoordinates.setLongitude(longitude.text.toString().toDouble())
                navController = Navigation.findNavController(it)

                if (navController.currentDestination?.id == R.id.insertFragment) {
                    navController.popBackStack()
                }

            }catch (e:NumberFormatException){
                Toast.makeText(requireContext(),"Insert correct coordinates !",Toast.LENGTH_LONG).show()
            }


        }
    }

    private fun initDestinationViewModel() {
        destinationViewModel.initShared(requireContext())
        lifecycleScope.launch {
            destinationViewModel.dataState.collect {
                when (it) {
                    is DataState.Success -> {
                        latitude.text = it.coordinates.latitude.toString().toEditable()
                        longitude.text = it.coordinates.longitude.toString().toEditable()

                    }
                    is DataState.Error -> {
                    }
                    is DataState.Loading -> {
                        println()
                    }
                }
            }
        }

        lifecycleScope.launch {
            destinationViewModel.userIntent.send(Intent.GetCoordinates)
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