package uk.fernando.tapup.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "tapup_data_store"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(STORE_NAME)

class PrefsStoreImpl(context: Context) : PrefsStore {

    private val dataStore = context.dataStore

    override suspend fun score(): Int {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.SCORE] ?: 0 }.first()
    }

    override suspend fun userID(): String {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.USER_ID] ?: "" }.first()
    }

    override suspend fun userName(): String {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.USER_NAME] ?: "" }.first()
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

    private object PreferencesKeys {
        val SCORE = intPreferencesKey("score")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
    }
}