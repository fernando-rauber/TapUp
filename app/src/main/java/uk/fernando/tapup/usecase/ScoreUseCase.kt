package uk.fernando.tapup.usecase

import android.content.ContentValues.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import uk.fernando.logger.MyLogger
import uk.fernando.tapup.model.ScoreModel
import uk.fernando.tapup.repository.ScoreRepository

class ScoreUseCase(private val repository: ScoreRepository, private val prefsUseCase: PrefUseCase, private val logger: MyLogger) {

    val top10ScoreList = MutableStateFlow<List<ScoreModel>>(emptyList())
    private var bestScore: Int = 0
    private var userID: String = ""
    private var userName: String = ""

    suspend fun getUserBestScore() = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            getGlobalScoreList()

            bestScore = prefsUseCase.getScore()
            userID = prefsUseCase.getUserId()
            userName = prefsUseCase.getUserName()

            checkIfUserGotIntoTop10()

            bestScore
        }.onFailure {
            logger.e(TAG, it.message.toString())
            logger.addMessageToCrashlytics(TAG, "Error on getUserBestScore - msg: ${it.message}")
            logger.addExceptionToCrashlytics(it)
        }.getOrElse { bestScore }
    }

    private suspend fun checkIfUserGotIntoTop10() {
        kotlin.runCatching {
            val isPlayerOnBoard = top10ScoreList.value.firstOrNull { it.uuid == userID }
            if (isPlayerOnBoard != null) {
                if (isPlayerOnBoard.score < bestScore) {
                    val newTop10 = top10ScoreList.value.toMutableList()

                    val indexPlayer = newTop10.indexOf(isPlayerOnBoard)

                    newTop10[indexPlayer].score = bestScore

                    // Update top 10
                    withContext(Dispatchers.IO) {
                        repository.updateScoreList(newTop10.sortedByDescending { it.score }.take(10))
                        getGlobalScoreList()
                    }
                }
            } else if (top10ScoreList.value.firstOrNull { it.score < bestScore } != null) {

                val newTop10 = top10ScoreList.value.toMutableList()

                newTop10.add(ScoreModel(userID, userName, bestScore))

                // Update top 10
                withContext(Dispatchers.IO) {
                    repository.updateScoreList(newTop10.sortedByDescending { it.score }.take(10))
                    getGlobalScoreList()
                }
            }
        }.onFailure {
            logger.e(TAG, it.message.toString())
            logger.addMessageToCrashlytics(TAG, "Error on checkIfUserGotIntoTop10 - msg: ${it.message}")
            logger.addExceptionToCrashlytics(it)
        }
    }

    suspend fun updateUserNameAtBoardTop10() {
        kotlin.runCatching {
            userID = prefsUseCase.getUserId()
            userName = prefsUseCase.getUserName()

            repository.getScoreBoardList().collect { top10 ->
                val isPlayerOnBoard = top10.firstOrNull { it.uuid == userID }
                if (isPlayerOnBoard != null) {

                    val indexPlayer = top10.indexOf(isPlayerOnBoard)

                    top10[indexPlayer].name = userName

                    // Update name on top 10
                    withContext(Dispatchers.IO) {
                        repository.updateScoreList(top10)
                        getGlobalScoreList()
                    }
                }
            }
        }.onFailure {
            logger.e(TAG, it.message.toString())
            logger.addMessageToCrashlytics(TAG, "Error on updateUserNameAtBoardTop10 - msg: ${it.message}")
            logger.addExceptionToCrashlytics(it)
        }
    }

    private suspend fun getGlobalScoreList() {
//        withContext(Dispatchers.IO) {
//            val newList = listOf(
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 10),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 50),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 100),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 150),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 200),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 250),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 300),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 350),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 400),
//                ScoreModel(UUID.randomUUID().toString(), "Player${(100..999).random()}", 450),
//            )
//
//            repository.updateScoreList(newList.sortedByDescending { it.score })
//        }
        repository.getScoreBoardList().collect {
            top10ScoreList.value = it
        }
    }
}