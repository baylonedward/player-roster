package com.baylonedward.player_roster.data.repository

import com.baylonedward.player_roster.data.local.room.dao.TeamDao
import com.baylonedward.player_roster.data.local.room.entity.Team
import com.baylonedward.player_roster.di.CoroutinesDispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
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