package com.lukieoo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.lukieoo.compassnetguru.model.Coordinates
import com.lukieoo.compassnetguru.ui.MainViewModel
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var coordinates: Coordinates

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mainViewModel =
            MainViewModel(SavedStateHandle())

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