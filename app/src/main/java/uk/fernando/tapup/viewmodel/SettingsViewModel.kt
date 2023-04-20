package uk.fernando.tapup.viewmodel

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.fernando.tapup.datastore.PrefsStore
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val prefsStore: PrefsStore) : BaseViewModel() {

    val playerName = mutableStateOf("")

    init {
        launch {
            playerName.value = prefsStore.userName()
        }
    }

    fun updateName(newName: String) {
        launchDefault {
            prefsStore.storeUserName(newName)
            playerName.value = newName
        }
    }
}
