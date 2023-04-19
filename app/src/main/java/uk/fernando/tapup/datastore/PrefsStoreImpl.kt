package uk.fernando.tapup.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.*

private const val STORE_NAME = "tapup_data_store"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(STORE_NAME)

class PrefsStoreImpl(context: Context) : PrefsStore {

    private val dataStore = context.dataStore

    override suspend fun score(): Int {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.SCORE] ?: 0 }.first()
    }

    override suspend fun userID(): String {
        return dataStore.data.map { prefs ->
            if (prefs[PreferencesKeys.USER_ID] != null)
                prefs[PreferencesKeys.USER_ID]!!
            else {
                val newID = UUID.randomUUID().toString()
                storeUserID(newID)

                newID
            }
        }.first()
    }

    override suspend fun userName(): String {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.USER_NAME] ?: "Default" }.first()
    }

    override fun isSoundEnabled(): Flow<Boolean> {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.SOUND_ENABLED] ?: true }
    }

    override suspend fun storeScore(score: Int) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.SCORE] = score }
    }

    override suspend fun storeUserID(id: String) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.USER_ID] = id }
    }

    override suspend fun storeUserName(name: String) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.USER_NAME] = name }
    }

    override suspend fun storeSound(enabled: Boolean) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.SOUND_ENABLED] = enabled }
    }

    private object PreferencesKeys {
        val SCORE = intPreferencesKey("score")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
    }
}