package uk.fernando.tapup.usecase

import uk.fernando.tapup.datastore.PrefsStore
import java.util.*

class PrefUseCase(private val prefsStore: PrefsStore) {

    suspend fun getScore() = prefsStore.score()

    suspend fun updateScore(newScore: Int) {
        prefsStore.storeScore(newScore)
    }

    suspend fun getUserId(): String {
        var userId = prefsStore.userID()

        if (userId.isEmpty()) {
            userId = UUID.randomUUID().toString()
            prefsStore.storeUserID(userId)
        }

        return userId
    }

    suspend fun getUserName(): String {
        var userName = prefsStore.userName()

        if (userName.isEmpty()) {
            userName = "Player${(100..999).random()}"
            prefsStore.storeUserName(userName)
        }

        return userName
    }

    suspend fun updateName(newName: String) {
        prefsStore.storeUserName(newName)
    }
}