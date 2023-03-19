package uk.fernando.tapup.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import uk.fernando.tapup.usecase.GameUseCase

open class GameViewModel(private val useCase: GameUseCase) : BaseViewModel() {

    val lastNumberSelected = mutableStateOf(1)

    private val _currentNumbers = mutableStateListOf<Int>()
    val currentNumbers: List<Int> = _currentNumbers


    fun startGame() {
        fetchNextNumbers()

        chronometer.start()


    }

    fun onNumberClick(number: Int){
       if(useCase.setNewNumber(number)){
           lastNumberSelected.value = number
           fetchNextNumbers()
       }else{
           // Game over
       }
    }

    private fun fetchNextNumbers(){
        launchDefault {
            _currentNumbers.clear()
            _currentNumbers.addAll(useCase.generateNextNumbers())
        }
    }

    val chronometerSeconds = mutableStateOf(61)

    private val chronometer = object : CountDownTimer(61000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            chronometerSeconds.value--
        }

        override fun onFinish() {
            chronometerSeconds.value = 0
        }
    }
}