package uk.fernando.tapup.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.flow
import uk.fernando.tapup.ext.toDeferredAsync
import uk.fernando.tapup.model.ScoreModel


class ScoreRepositoryImpl(database: FirebaseDatabase) : ScoreRepository {

    private val databaseRef = database.getReference("SCORE")

    override suspend fun getScoreBoardList() = flow {
        val dataSnapshot = databaseRef.toDeferredAsync().await()

        dataSnapshot.getValue<List<ScoreModel>>()?.let { scoreList ->
            emit(scoreList)
        }
    }

    override suspend fun updateScoreList(scoreList: List<ScoreModel>) {
        databaseRef.setValue(scoreList)
    }
}