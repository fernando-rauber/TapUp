package uk.fernando.tapup.viewmodel

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.fernando.tapup.model.ScoreModel
import uk.fernando.tapup.usecase.ScoreUseCase
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(private val useCase: ScoreUseCase) : BaseViewModel() {

    val globalScore = mutableStateOf(emptyList<ScoreModel>())
    val myScore = mutableStateOf(0)
    val myBestScore = mutableStateOf(0)

    init {
        launch {
            useCase.top10ScoreList.collect { scoreList ->
                globalScore.value = scoreList
            }
        }
    }

    fun fetchAllScores(newScore: Int) {
        launchDefault {
            myBestScore.value = useCase.getUserBestScore(newScore)
            myScore.value = newScore
        }
    }
}
