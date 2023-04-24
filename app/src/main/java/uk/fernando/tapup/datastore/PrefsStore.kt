package uk.fernando.tapup.datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {

    suspend fun score(): Int
    suspend fun userID(): String
    suspend fun userName(): String
    fun isSoundEnabled(): Flow<Boolean>

    suspend fun storeScore(score: Int)
    suspend fun storeUserID(id: String)
    suspend fun storeUserName(name: String)
    suspend fun storeSound(enabled: Boolean)
}