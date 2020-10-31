package com.lukieoo.compassnetguru.Intent

import com.lukieoo.compassnetguru.model.Coordinates


/**
 * Data state management handle event
 */
sealed class DataState {
    object Idle : DataState()
    data class Success(val coordinates : Coordinates) : DataState()
    data class Error(val exception: Exception) : DataState()
    object Loading : DataState()
}
