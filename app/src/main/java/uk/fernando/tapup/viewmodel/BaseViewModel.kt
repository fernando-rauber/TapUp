package uk.fernando.tapup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun launchDefault(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) { block() }
    }

    fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
