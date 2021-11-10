package com.baylonedward.player_roster.data.repository

import com.baylonedward.player_roster.data.local.room.dao.PlayerDao
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.di.CoroutinesDispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */

@ExperimentalCoroutinesApi
@Singleton
class PlayersRepository @Inject constructor(
    private val playerDao: PlayerDao,
    @CoroutinesDispatchersModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    fun allPlayers(): Flow<List<Player>> = channelFlow {
        playerDao.all().collect { send(it) }
    }.flowOn(dispatcher)

    suspend fun addPlayer(player: Player): Boolean {
        return withContext(dispatcher) {
            try {
                // check if same name exist
                val exist =
                    allPlayers().firstOrNull()?.find { it.name == player.name }
                if (exist != null) return@withContext false

                playerDao.insert(player)
                true
            } catch (e: Exception) {
                println("Add Player: $e")
                false
            }
        }
    }
}