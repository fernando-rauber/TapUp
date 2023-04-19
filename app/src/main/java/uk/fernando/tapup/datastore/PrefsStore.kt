package uk.fernando.tapup.datastore

interface PrefsStore {

    suspend fun score(): Int
    suspend fun userID(): String
    suspend fun userName(): String

    suspend fun storeScore(score: Int)
    suspend fun storeUserID(id: String)
    suspend fun storeUserName(name: String)
}