package com.lukieoo.compassnetguru.ui

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.lukieoo.compassnetguru.model.Coordinates
import com.lukieoo.compassnetguru.Intent.DataState
import com.lukieoo.compassnetguru.Intent.Intent
import com.lukieoo.compassnetguru.utils.MyCoordinates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.*
import java.lang.Exception

@ExperimentalCoroutinesApi
class DestinationViewModel
@ViewModelInject
constructor(  @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {


    lateinit var myCoordinates: MyCoordinates

    val userIntent = Channel<Intent>(Channel.UNLIMITED)

    private val _dataState= MutableStateFlow<DataState>(
        DataState.Idle)

    val dataState: StateFlow<DataState>
        get() = _dataState

    init {

        setDestinationEvent()
    }
    fun initShared(context:Context){
         myCoordinates= MyCoordinates(context)
    }
    fun setDestinationEvent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is Intent.GetCoordinates -> {
                        getDestination()
                            .onEach {
                                _dataState.value = it
                            }
                            .launchIn(viewModelScope)
                    }

                    Intent.None -> {
                    }
                }

            }


        }
    }
    private suspend fun getDestination(): Flow<DataState> = flow {
        emit(DataState.Loading)
        delay(300)
        try{
            var coordinates:Coordinates = Coordinates(myCoordinates.getLatitude(),(myCoordinates.getLongitude()))

            emit(DataState.Success(coordinates))

        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

}




















