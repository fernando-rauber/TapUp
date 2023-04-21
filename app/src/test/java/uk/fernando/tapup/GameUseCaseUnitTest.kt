package uk.fernando.tapup

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import uk.fernando.logger.AndroidLogger
import uk.fernando.logger.MyLogger
import uk.fernando.tapup.usecase.GameUseCase

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GameUseCaseUnitTest {

    private val useCase: GameUseCase = GameUseCase(AndroidLogger(MyLogger.LogLevel.DEBUG))

    @Test
    fun `check if return next number`() = runBlocking {
        assertEquals(true, useCase.getNextNumber() > -10)
    }

    @Test
    fun `check if new number is bigger than the previous`()  {
        assertThat( useCase.setNewNumber(1)).isEqualTo(false)
        assertEquals(true, useCase.setNewNumber(2))
        assertEquals(false, useCase.setNewNumber(2))
        assertEquals(true, useCase.setNewNumber(5))
    }

    @Test
    fun `check if return next number when set a higher number`() = runTest {
        assertEquals(true, useCase.getNextNumber() < 22)

        assertEquals(true, useCase.setNewNumber(500))

        assertEquals(true, useCase.getNextNumber() > 489)
    }
}