package uk.fernando.tapup.usecase

import android.content.ContentValues
import uk.fernando.logger.MyLogger

class GameUseCase(private val logger: MyLogger) {

    private var currentMaxNumber = 1
    private val numberList = mutableListOf<Int>()
    private var nextNumberIndex = -1
    private var mistakesLeft = 3

    init {
        generateNextNumbers()
    }

    fun reset() {
        currentMaxNumber = 1
        numberList.clear()
        nextNumberIndex = -1
        mistakesLeft = 3

        generateNextNumbers()
    }

    fun setNewNumber(number: Int): Boolean {
        return if (number > currentMaxNumber) {
            currentMaxNumber = number
            generateNextNumbers()
            true
        } else {
            mistakesLeft--
            false
        }
    }

    fun getMistakesLeft() = mistakesLeft

    fun getNextNumber(): Int {
        nextNumberIndex++

        if (nextNumberIndex > (numberList.size - 1))
            nextNumberIndex = 0

        return numberList[nextNumberIndex]
    }

    private fun generateNextNumbers() {
        kotlin.runCatching {
            numberList.clear()

            for (i in 1..4) {
                numberList.add(createNewNumber())
            }

            if (numberList.firstOrNull { it > currentMaxNumber } == null)
                generateNextNumbers()
        }.onFailure {
            logger.e(ContentValues.TAG, it.message.toString())
            logger.addMessageToCrashlytics(ContentValues.TAG, "Error on generateNextNumbers - msg: ${it.message}")
            logger.addExceptionToCrashlytics(it)
        }
    }

    private fun createNewNumber(): Int {
        val newNumber = (currentMaxNumber - 10..currentMaxNumber + 10).random()

        return if (numberList.contains(newNumber))
            createNewNumber()
        else
            newNumber
    }
}