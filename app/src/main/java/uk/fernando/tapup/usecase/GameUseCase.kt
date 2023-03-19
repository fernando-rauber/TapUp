package uk.fernando.tapup.usecase

import androidx.compose.runtime.mutableStateOf
import uk.fernando.logger.MyLogger
import uk.fernando.util.ext.TAG
import kotlin.math.sqrt

class GameUseCase(private val logger: MyLogger) {

    var currentMaxNumber = 1

    fun  generateNextNumbers() : List<Int>{

        return emptyList()
    }

    fun setNewNumber(number: Int) : Boolean{
        return if(number > currentMaxNumber) {
            currentMaxNumber = number
            true
        } else
            false
    }
}