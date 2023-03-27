package uk.fernando.tapup.repository

import kotlinx.coroutines.flow.Flow
import uk.fernando.tapup.model.ScoreModel

interface ScoreRepository {

    suspend fun getScoreBoardList(): Flow<List<ScoreModel>>
    suspend fun updateScoreList(scoreList: List<ScoreModel>)
}