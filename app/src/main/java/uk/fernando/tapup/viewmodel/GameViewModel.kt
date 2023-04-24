package uk.fernando.tapup.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import uk.fernando.tapup.usecase.GameUseCase
import uk.fernando.tapup.usecase.PrefUseCase
import uk.fernando.tapup.util.GameStatus
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val useCase: GameUseCase, private val prefsUseCase: PrefUseCase) : BaseViewModel() {

    val lastNumberSelected = mutableStateOf(10)
    val currentNumber = mutableStateOf(0)
    val mistakeLeft = mutableStateOf(3)
    val gameStatus = mutableStateOf(GameStatus.INIT)
    val isSoundEnable = mutableStateOf(false)

    init {
        launch {
            isSoundEnable.value = prefsUseCase.isSoundEnabled()
        }
    }

    fun startGame() {
        chronometer.start()
        mistakeLeft.value = useCase.getMistakesLeft()
    }

    fun onNumberClick(number: Int) = flow {
        val isNewNumberHigher = useCase.setNewNumber(number)

        if (isNewNumberHigher) {
            lastNumberSelected.value = number
            emit(GameStatus.CORRECT)
        } else {
            mistakeLeft.value = useCase.getMistakesLeft()
            emit(GameStatus.WRONG)

            // Game over
            if (mistakeLeft.value <= 0) {
                chronometer.onFinish()
                chronometer.cancel()
            }
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
            gameStatus.value = GameStatus.END_GAME
            launchDefault { prefsUseCase.updateScore(lastNumberSelected.value) }
        }
    }
}