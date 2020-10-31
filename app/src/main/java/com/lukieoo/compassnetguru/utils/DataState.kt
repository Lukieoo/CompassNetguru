package com.lukieoo.compassnetguru.utils

import com.lukieoo.compassnetguru.model.Coordinates


/**
 * Not my standard DataState class. I wanted to simplify things for this Hilt example.
 * My standard implementation is much more complex but handles a wide array of use cases.
 * see https://github.com/mitchtabian/Clean-Notes/blob/master/app/src/main/java/com/codingwithmitch/cleannotes/business/domain/state/DataState.kt
 */
sealed class DataState {
    object Idle : DataState()
    data class Success(val coordinates : Coordinates) : DataState()
    data class Error(val exception: Exception) : DataState()
    object Loading : DataState()
}
