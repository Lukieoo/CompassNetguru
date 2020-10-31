package com.lukieoo.compassnetguru.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.lukieoo.compassnetguru.model.Coordinates
import com.lukieoo.compassnetguru.utils.DataState
import com.lukieoo.compassnetguru.utils.Intent
import com.lukieoo.compassnetguru.utils.MyCoordinates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(    @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    @Inject
    lateinit var myCoordinates: MyCoordinates

    val userIntent = Channel<Intent>(Channel.UNLIMITED)

    private val _dataState= MutableStateFlow<DataState>(DataState.Idle)

    val dataState: StateFlow<DataState>
        get() = _dataState
    init {
        setStateEvent()
    }
    fun setStateEvent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is Intent.GetCoordinates -> {
                        getTarget()
                            .onEach {
                                _dataState.value = it
                            }
                            .launchIn(viewModelScope)
                    }

                    Intent.None -> {
                        // who cares
                    }
                }

            }


        }
    }

    suspend fun getTarget(): Flow<DataState> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            var coordinates:Coordinates = Coordinates(myCoordinates.getLatitude(),(myCoordinates.getLongitude()))

            emit(DataState.Success(coordinates))

        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

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




















