package com.baylonedward.player_roster.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baylonedward.player_roster.data.local.room.dao.TeamDao
import com.baylonedward.player_roster.data.local.room.entity.Team

/**
 * Created by: ebaylon.
 * Created on: 11/09/2021.
 */

@Database(
  entities = [Team::class],
  version = 1,
  exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
  // team dao
  abstract fun teamDao(): TeamDao

  companion object {
    @Volatile
    private var instance: LocalDatabase? = null

    fun getDatabase(context: Context): LocalDatabase =
      instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

    private fun buildDatabase(appContext: Context) =
      Room.databaseBuilder(appContext, LocalDatabase::class.java, "local_db")
        .fallbackToDestructiveMigration()
        .build()
  }
}