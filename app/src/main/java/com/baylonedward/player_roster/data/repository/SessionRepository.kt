package com.baylonedward.player_roster.data.repository

import android.content.SharedPreferences
import com.baylonedward.player_roster.data.local.room.LocalDatabase
import kotlin.concurrent.thread

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */

class SessionRepository private constructor(
    private val sharedPreferences: SharedPreferences,
    private val localDatabase: LocalDatabase
) {

    private val editor = sharedPreferences.edit()

    var firsTime: Boolean
        get() = sharedPreferences.getBoolean(FIRST_TIME_KEY, true)
        set(value) = editor.putBoolean(FIRST_TIME_KEY, value).apply()

    fun clear() {
        thread { localDatabase.clearAllTables() }
        editor.clear().commit()
    }

    companion object {
        private const val FIRST_TIME_KEY = "firstTimeKey"

        @Volatile
        private var instance: SessionRepository? = null
        fun getInstance(sharedPreferences: SharedPreferences, database: LocalDatabase) = instance ?: synchronized(this) {
            SessionRepository(sharedPreferences, database).also { instance = it }
        }
    }
}