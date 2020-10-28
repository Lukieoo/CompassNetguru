package com.lukieoo.compassnetguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lukieoo.compassnetguru.utils.Compass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}