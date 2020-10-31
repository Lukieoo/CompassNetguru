package com.lukieoo.compassnetguru.utils

sealed class Intent() {
    object GetCoordinates: Intent()

    object None: Intent()
}