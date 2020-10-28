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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_insert.*

@AndroidEntryPoint
class InsertFragment constructor() : Fragment(R.layout.fragment_insert) {

    lateinit var navController: NavController

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        btnSetCoordinates.setOnClickListener {
            navController = Navigation.findNavController(it)
            navController!!.navigate(R.id.action_insertFragment_to_mainFragment)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        println("Cordinate  ")
        activity?.let {
            viewModel.getCoordinates().observe(it, Observer {
                println("Cordinate  ${it.longitude}  :  ${it.latitude}")
//                requireActivity().runOnUiThread(Runnable {
                if (it.latitude != null && it.longitude != null) {
                    if (latitudeCurrent != null &&longitudeCurrent !=null) {
                        latitudeCurrent.text = it.latitude.toString().toEditable()
                        longitudeCurrent.text = it.longitude.toString().toEditable()
                    }

                }
            })


        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.getCoordinates().removeObservers (this)
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}