package com.lukieoo.compassnetguru.utils

class MathematicalOperations {

    fun distance(
        lat1: Double,
        lng1: Double,
        lat2: Double,
        lng2: Double
    ): Double {
        val theta = lng1 - lng2
        var dist =
            Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(
                deg2rad(lat1)
            ) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        dist *= 1.609344

        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }


    fun azimuth(x1: Double, y1: Double, x2: Double, y2: Double): Double {

        var pi: Double
        var dx: Double
        var dy: Double
        var a: Double
        var az: Double

        pi = 4.0 * Math.atan(1.0)
        dx = x2 - x1
        dy = y2 - y1
        if (dx == 0.0) {
            if (dy > 0) a = pi / 2 else a = 1.5 * pi
        } else {
            a = Math.atan(dy / dx)
            if (dx < 0) {
                a = a + pi
            } else {
                if (dy < 0) a = a + 2 * pi
            }


        }
        az = a  *180/Math.PI
        return az
    }
}

