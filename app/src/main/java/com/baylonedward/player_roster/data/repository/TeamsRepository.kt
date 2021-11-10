package com.baylonedward.player_roster.data.repository

import com.baylonedward.player_roster.data.local.room.dao.TeamDao
import com.baylonedward.player_roster.data.local.room.entity.Team
import com.baylonedward.player_roster.di.CoroutinesDispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */

@ExperimentalCoroutinesApi
@Singleton
class TeamsRepository @Inject constructor(
    private val teamDao: TeamDao,
    @CoroutinesDispatchersModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    fun allTeams(): Flow<List<Team>> = channelFlow {
        teamDao.all().collect { send(it) }
    }.flowOn(dispatcher)

    suspend fun addTeam(team: Team): Boolean {
        return withContext(dispatcher) {
            try {
                // check if same name and city exist
                val exist =
                    allTeams().firstOrNull()?.find { it.name == team.name && it.city == team.city }
                if (exist != null) return@withContext false

                teamDao.insert(team)
                true
            } catch (e: Exception) {
                println("Add Team: $e")
                false
            }
        }
    }

    suspend fun addTeams(list: List<Team>): Boolean {
        return withContext(dispatcher) {
            try {
                teamDao.insert(list)
                true
            } catch (e: Exception) {
                println("Add Teams: $e")
                false
            }
        }
    }
}