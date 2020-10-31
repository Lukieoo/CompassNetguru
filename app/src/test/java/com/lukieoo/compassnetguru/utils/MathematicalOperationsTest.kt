package com.lukieoo.compassnetguru.utils

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class MathematicalOperationsTest{

    lateinit var mathematicalOperations:MathematicalOperations

    @Before
    fun setUpTest(){
        mathematicalOperations =MathematicalOperations()
    }

    @Test
    fun testCalculateDistance(){
       var distance= mathematicalOperations.distance(
            lat1 =10.0,
            lat2 =10.0,
            lng1 =13.0,
            lng2 =12.0
        ).toInt()

        assertEquals("", 109,distance)
    }

    @Test
    fun testAz1(){
        var distance= mathematicalOperations.azimuth(
             10.0,
             10.0,
            13.0,
             12.0
        ).toInt()

        assertEquals("", 33,distance)
    }

    @Test
    fun testAz2(){
        var distance= mathematicalOperations.azimuth(
            0.0,
            0.0,
            13.0,
            12.0
        ).toInt()

        assertEquals("", 42,distance)
    }
}