package com.lukieoo.compassnetguru.utils

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class CalculateDistanceTest{

    lateinit var calculateDistance:CalculateDistance

    @Before
    fun setUpTest(){
        calculateDistance =CalculateDistance()
    }

    @Test
    fun testCalculateDistance(){
       var distance= calculateDistance.distance(
            lat1 =10.0,
            lat2 =10.0,
            lng1 =13.0,
            lng2 =12.0
        ).toInt()

        assertEquals("", 109,distance)
    }

    @Test
    fun testAz1(){
        var distance= calculateDistance.az1(
             10.0,
             10.0,
            13.0,
             12.0
        ).toInt()

        assertEquals("", 33,distance)
    }
}