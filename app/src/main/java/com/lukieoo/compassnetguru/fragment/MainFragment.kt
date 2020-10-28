package com.lukieoo.compassnetguru.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.lukieoo.compassnetguru.R
import com.lukieoo.compassnetguru.utils.Compass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment constructor() : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var compass: Compass

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compass.setImageViewCompass(imageViewCompass = imageViewCompass)
        compass.setDegreeTitle(degreeTitle = degreeTitle)

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