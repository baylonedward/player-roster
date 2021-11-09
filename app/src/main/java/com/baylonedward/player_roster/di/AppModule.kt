package com.baylonedward.player_roster.di

import android.content.Context
import com.baylonedward.player_roster.MainApp
import com.baylonedward.player_roster.data.local.room.LocalDatabase
import com.baylonedward.player_roster.data.local.room.dao.TeamDao
import com.baylonedward.player_roster.data.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideTeamDao(db: LocalDatabase): TeamDao {
        return db.teamDao()
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        @ApplicationContext context: Context,
        database: LocalDatabase
    ): SessionRepository {
        return SessionRepository.getInstance(
            sharedPreferences = context.getSharedPreferences(
                MainApp.sharedPreferenceName,
                Context.MODE_PRIVATE
            ),
            database = database
        )
    }
}