package uk.fernando.tapup.datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {

    fun isSoundEnabled(): Flow<Boolean>

    suspend fun storeSound(enabled: Boolean)
}