package com.lukieoo.compassnetguru.Intent

sealed class Intent() {
    object GetCoordinates: Intent()

    object None: Intent()
}