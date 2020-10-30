package com.lukieoo.compassnetguru.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukieoo.compassnetguru.model.Coordinates
import com.lukieoo.compassnetguru.utils.MyCoordinates
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var coordinates:Coordinates

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setUp() {
        mainViewModel = MainViewModel()

    }

    @Test
    fun testModel(){
        coordinates= Coordinates(50.5,50.5)
        mainViewModel.setCoordinates(coordinates)

        assertThat(mainViewModel.getCoordinates(), not(nullValue()))
    }
    @After
    fun tearDown() {
    }
}