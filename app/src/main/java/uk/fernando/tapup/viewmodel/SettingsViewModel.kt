package uk.fernando.tapup.viewmodel

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.fernando.tapup.datastore.PrefsStore
import uk.fernando.tapup.usecase.ScoreUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val prefsStore: PrefsStore, private val useCase: ScoreUseCase) : BaseViewModel() {

    val playerName = mutableStateOf("")

    init {
        launch {
            playerName.value = prefsStore.userName().substringBefore("_")
        }
    }

    fun updateName(newName: String) {
        launchDefault {
            prefsStore.storeUserName(newName)
            playerName.value = newName

            useCase.updateUserNameAtBoardTop10()
        }
    }
}
