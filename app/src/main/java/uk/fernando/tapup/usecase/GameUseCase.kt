package uk.fernando.tapup.usecase

import uk.fernando.logger.MyLogger

class GameUseCase(private val logger: MyLogger) {

    private var currentMaxNumber = 10
    private val numberList = mutableListOf<Int>()
    private var nextNumberIndex = -1
    private var mistakesLeft = 3

    init {
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
        numberList.clear()

        for (i in 1..4) {
            numberList.add(createNewNumber())
        }

        if (numberList.firstOrNull { it > currentMaxNumber } == null)
            generateNextNumbers()
    }

    private fun createNewNumber(): Int {
        val newNumber = (currentMaxNumber - 10..currentMaxNumber + 20).random()

        return if (numberList.contains(newNumber))
            createNewNumber()
        else
            newNumber
    }
}