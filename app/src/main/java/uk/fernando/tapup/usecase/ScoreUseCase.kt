package uk.fernando.tapup.usecase

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import uk.fernando.logger.MyLogger
import uk.fernando.tapup.datastore.PrefsStore
import uk.fernando.tapup.model.ScoreModel
import uk.fernando.tapup.repository.ScoreRepository
import uk.fernando.util.ext.TAG

class ScoreUseCase(private val repository: ScoreRepository, private val prefs: PrefsStore, private val logger: MyLogger) {

    val top10ScoreList = MutableStateFlow<List<ScoreModel>>(emptyList())
    private var bestScore: Int = 0
    private var userID: String = ""
    private var userName: String = ""

    suspend fun getUserBestScore(newScore: Int) = withContext(Dispatchers.IO) {
        getGlobalScoreList()

        var userBestScore = prefs.score()

        if (userBestScore < newScore) {
            prefs.storeScore(newScore)
            userBestScore = newScore
        }

        bestScore = userBestScore
        userID = prefs.userID()
        userName = prefs.userName()

        checkIfUserGotIntoTop10()

        bestScore
    }

    private suspend fun checkIfUserGotIntoTop10() {
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
            Log.e(TAG, "checkIfUserGotIntoTop10: ")

            val newTop10 = top10ScoreList.value.toMutableList()

            newTop10.add(ScoreModel(userID, userName, bestScore))

            // Update top 10
            withContext(Dispatchers.IO) {
                repository.updateScoreList(newTop10.sortedByDescending { it.score }.take(10))
                getGlobalScoreList()
            }
        }
    }

    private suspend fun getGlobalScoreList() {
//        withContext(Dispatchers.IO) {
//            val newList = listOf(
//                ScoreModel("11111", "User 1", 10),
//                ScoreModel("22222", "User 2", 100),
//                ScoreModel("33333", "User 3", 200),
//                ScoreModel("44444", "User 4", 300),
//                ScoreModel("55555", "User 5", 400),
//                ScoreModel("66666", "User 6", 500),
//                ScoreModel("77777", "User 7", 600),
//                ScoreModel("88888", "User 8", 700),
//                ScoreModel("99999", "User 9", 800),
//                ScoreModel("00000", "User 10", 900),
//            )
//
//            repository.updateScoreList(newList.sortedByDescending { it.score })
//        }
        repository.getScoreBoardList().collect {
            top10ScoreList.value = it
        }
    }
}