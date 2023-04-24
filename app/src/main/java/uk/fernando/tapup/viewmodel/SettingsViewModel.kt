package uk.fernando.tapup.viewmodel

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.fernando.tapup.usecase.PrefUseCase
import uk.fernando.tapup.usecase.ScoreUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val prefsUseCase: PrefUseCase, private val useCase: ScoreUseCase) : BaseViewModel() {

    val playerID = mutableStateOf("")
    val playerName = mutableStateOf("")
    val isSoundEnable = mutableStateOf(true)

    init {
        launch {
            playerID.value = prefsUseCase.getUserId().take(6)
            playerName.value = prefsUseCase.getUserName()
            isSoundEnable.value = prefsUseCase.isSoundEnabled()
        }
    }

    fun updateName(newName: String) {
        launchDefault {
            prefsUseCase.updateName(newName)
            playerName.value = newName

            useCase.updateUserNameAtBoardTop10()
        }
    }

    fun updateSound() {
        launch {
            prefsUseCase.updateMusicEnable()
            isSoundEnable.value = !isSoundEnable.value
        }
    }
}
