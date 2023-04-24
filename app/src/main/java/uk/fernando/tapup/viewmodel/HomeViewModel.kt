package uk.fernando.tapup.viewmodel

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.fernando.tapup.usecase.PrefUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val prefsUseCase: PrefUseCase) : BaseViewModel() {

    val isSoundEnable = mutableStateOf(true)

    init {
        launch {
            isSoundEnable.value = prefsUseCase.isSoundEnabled()
        }
    }

    fun updateSound() {
        launch {
            prefsUseCase.updateMusicEnable()
            isSoundEnable.value = !isSoundEnable.value
        }
    }
}
