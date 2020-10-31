package com.lukieoo.compassnetguru.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.lukieoo.compassnetguru.model.Coordinates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(  @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {


    private lateinit var coordinates: MutableLiveData<Coordinates>

    init {
        println(getCoordinates().value)
    }

    open fun getCoordinates(): MutableLiveData<Coordinates> {
        if (!this::coordinates.isInitialized) {
            coordinates = MutableLiveData<Coordinates>()
        }
        if (coordinates == null) {
            coordinates = MutableLiveData<Coordinates>()
        }
        return coordinates
    }

    open fun setCoordinates(coord: Coordinates) {
        viewModelScope.launch {
            coordinates.value = coord
        }
    }


}




















