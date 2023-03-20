package uk.fernando.tapup.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import uk.fernando.tapup.usecase.GameUseCase

open class GameViewModel(private val useCase: GameUseCase) : BaseViewModel() {

    val lastNumberSelected = mutableStateOf(1)
    val currentNumber = mutableStateOf(0)

    fun startGame() {
        chronometer.start()
    }

    fun onNumberClick(number: Int) {
        val isNewNumberHigher = useCase.setNewNumber(number)
        if (isNewNumberHigher) {
            lastNumberSelected.value = number
        } else {
            // Game over
        }
    }

    val chronometerSeconds = mutableStateOf(61)

    private val chronometer = object : CountDownTimer(61000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            chronometerSeconds.value--
            currentNumber.value = useCase.getNextNumber()
        }

        override fun onFinish() {
            chronometerSeconds.value = 0
        }
    }
}